# Deployment Guide — Expensight on Railway

This guide walks you through deploying the Expensight application to Railway, from start to finish.

---

## Prerequisites

- A **GitHub** account (free)
- Git installed on your machine
- Your code pushed to a GitHub repository

---

## 1. Pre-deployment Checklist

Verify these before deploying:

| Item | Status |
|------|--------|
| `src/main/resources/application.properties` has `spring.jpa.hibernate.ddl-auto=update` | Required for Railway's empty database |
| `railway.json` exists at project root | ✅ Already present |
| `Dockerfile` exists at project root | ✅ Already present |
| No hardcoded `localhost` in frontend code | ✅ Uses relative URLs like `/api/expenses` |
| Database env vars use `${...}` placeholders | ✅ `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` |

### Critical: DDL Mode

If `application.properties` still says `spring.jpa.hibernate.ddl-auto=none`, change it to:

```properties
spring.jpa.hibernate.ddl-auto=update
```

This tells Hibernate to automatically create the required tables in Railway's MySQL database. Without this, the app will crash on startup with `Table '...' doesn't exist`.

---

## 2. Push Code to GitHub

```bash
# Initialize Git (if not already done)
git init
git add .
git commit -m "Initial commit"

# Create a branch named main
git branch -M main

# Add your GitHub repository as remote
git remote add origin https://github.com/YOUR_USERNAME/expensight.git

# Push to GitHub
git push -u origin main
```

> Replace `YOUR_USERNAME` with your actual GitHub username.

---

## 3. Create a Railway Account

1. Go to https://railway.com
2. Click **Login** and choose **Continue with GitHub** (recommended — easiest setup)
3. Authorize Railway to access your GitHub account
4. You will land on the Railway dashboard

---

## 4. Create a Railway Project

1. Click **New Project**
2. Select **Deploy from GitHub repo**
3. Find and select your `expensight` repository
4. Railway detects `railway.json` and starts building automatically

The build will initially fail because there is no database yet. That is expected — fix it in the next step.

---

## 5. Add MySQL Database

1. Click **New** at the top of the project dashboard
2. Select **Database** → **Add MySQL**
3. Wait ~30 seconds for Railway to provision the database
4. Click on the MySQL service card to view its details

Railway sets these environment variables on the MySQL service:

| Variable | Contains |
|----------|----------|
| `MYSQL_URL` | Full JDBC connection URL |
| `MYSQL_USER` | Database username |
| `MYSQL_PASSWORD` | Database password |

---

## 6. Configure Environment Variables

Now map the MySQL variables to the ones your app expects.

1. Click on your **application service** (the one running your Java app, not the MySQL service)
2. Go to the **Variables** tab
3. Add each variable by clicking **New Variable**:

| Variable Name | How to set the value |
|---------------|----------------------|
| `DB_URL` | Click **Reference** → select the MySQL service → select `MYSQL_URL` |
| `DB_USERNAME` | Click **Reference** → select the MySQL service → select `MYSQL_USER` |
| `DB_PASSWORD` | Click **Reference** → select the MySQL service → select `MYSQL_PASSWORD` |

> **What is a Reference?** Instead of typing a value, a Reference tells Railway "use the value from another service's variable". If the MySQL service changes its URL later, your app automatically gets the new value.

After adding all three, the Variables tab should list:

```
DB_URL          → (referenced from MySQL → MYSQL_URL)
DB_USERNAME     → (referenced from MySQL → MYSQL_USER)
DB_PASSWORD     → (referenced from MySQL → MYSQL_PASSWORD)
PORT            → (set by Railway automatically)
```

> Do **not** set `PORT` manually — Railway provides it automatically.

---

## 7. Trigger the First Deployment

1. Go to the **Deployments** tab of your application service
2. Click **Redeploy**
3. Railway rebuilds the Docker image and starts the app

---

## 8. Watch the Logs

1. While the deployment runs, click on it to open the log viewer
2. Look for these key messages in the logs:

**Build phase (Docker):**
```
Step 1/11 : FROM maven:3.9-eclipse-temurin-17 AS build
...
[INFO] BUILD SUCCESS
```

**Runtime phase (Spring Boot):**
```
Started ExpensightApplication in X seconds
Expensight is running...
```

**If you see errors:**

| Error in Logs | Cause | Fix |
|---------------|-------|-----|
| `Table 'expensight.users' doesn't exist` | DDL mode is `none` | Change to `ddl-auto=update` and push again |
| `Communications link failure` | Database env vars not set | Check Variables tab — `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` must be set |
| `Connection refused` | MySQL service not ready | Wait 30s and redeploy |
| Maven build failure | Code doesn't compile | Read the full Maven error for details |
| `Unable to find image` | Docker build issue | Go to Settings → Redeploy |

---

## 9. Redeploy After Code Changes

### Automatic (recommended)

Railway automatically redeploys whenever you push to your GitHub repository's `main` branch:

```bash
git add .
git commit -m "Your change description"
git push
```

Wait ~2 minutes, then check the Deployments tab for the new build.

### Manual

1. Go to your application service in Railway
2. **Deployments** tab → **Redeploy**

---

## 10. Get the Public URL

1. Click on your application service
2. Go to the **Settings** tab
3. Under **Networking**, click **Generate Domain**
4. Railway creates a URL like: `https://expensight.up.railway.app`
5. Click the URL to open your app

Your app is live at this URL. Share it with anyone.

---

## 11. Verification Checklist

Walk through this after deployment:

### Application Starts
- [ ] Visit `https://your-app.up.railway.app/` — the Expensight Dashboard loads
- [ ] Navigation bar appears with links: Dashboard, Users, Categories, Expenses, Add Expense

### Database Works
- [ ] Dashboard shows numbers (not `-`) for Total Users, Total Categories, Total Expenses, Total Amount
- [ ] If all show `-`, the database connection is broken — check env vars

### CRUD Operations
- [ ] **Users page** → users are listed → click Add User → success
- [ ] **Categories page** → categories are listed → click Add Category → success
- [ ] **Expenses page** → expenses are listed
- [ ] Click **Edit** on any row → modal opens with data filled → Save → success
- [ ] Click **Delete** on any row → confirm → row disappears

### Dashboard Updates
- [ ] After adding/deleting data, Dashboard numbers change

### Pagination
- [ ] If more than 20 expenses exist, pagination controls appear at the bottom
- [ ] Click **Next** → page changes

### Static Resources
- [ ] Page styling (colors, fonts, layout) loads correctly
- [ ] Bootstrap modals work when clicking Edit/Add buttons

### Browser Console
- [ ] Open Developer Tools (F12 → Console tab)
- [ ] No red error messages

---

## Troubleshooting Quick Reference

| Problem | Likely Cause | What to Do |
|---------|-------------|------------|
| Build fails | Code error or dependency issue | Read the build log, fix the error, push again |
| App starts but shows 502 | Not ready yet | Wait 30-60s and refresh |
| Health check failing | Database not connected | Verify `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` are set correctly |
| Tables missing | DDL mode is `none` | Change to `ddl-auto=update` and redeploy |
| "Unable to connect to server" in browser | App is down or restarting | Check deployment logs |
| Swagger UI returns 404 | Springdoc not loading properly | Verify `springdoc-openapi-starter-webmvc-ui` dependency in `pom.xml` |

---

## Environment Variables Reference

| Variable | Set by | Purpose |
|----------|--------|---------|
| `PORT` | Railway (automatic) | The port Railway routes traffic to |
| `DB_URL` | You (reference to MySQL) | JDBC connection URL for MySQL |
| `DB_USERNAME` | You (reference to MySQL) | MySQL username |
| `DB_PASSWORD` | You (reference to MySQL) | MySQL password |

---

## How It All Connects

```
Browser ──HTTPS──> Railway Router ──> Your App (port ${PORT})
                                            │
                                            ▼
                                    Railway MySQL (via DB_URL)
```

- Railway's router automatically maps `https://your-app.up.railway.app` to your app's internal port (`PORT`).
- Your app reads `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` from environment variables.
- Railway's MySQL service provides the database credentials automatically.
- On startup, Hibernate creates/updates tables because `ddl-auto=update`.
- `DataInitializer` seeds sample data if the `users` table is empty.
- Railway pings `GET /api/dashboard/summary` every ~30 seconds to verify the app is healthy.
