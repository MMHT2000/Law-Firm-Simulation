# Law Firm Simulation - Rebuild Plan

## Current State Analysis

**Working Features:**
- Welcome page with role selection (Client/Lawyer/Admin)
- Client registration/login with profile management
- Admin dashboard (add cases, view lawyers)
- Lawyer login/dashboard with profile display
- File-based storage for users/cases/lawyers

**Critical Issues:**
1. UI bug in `lawyerDashboard.java` line 44: uses undefined `userNameLabel`
2. Missing budget-range hiring flow (README feature incomplete)
3. Clients cannot view their own cases
4. Admin cannot edit/delete cases
5. Hardcoded admin credentials
6. No password security

---

## Phase 1: Fix Critical Bugs (Week 1)

### Task 1.1: Fix LawyerDashboard UI Bug
- **File:** `lawyerDashboard.java`
- **Line 44:** Change `userNameLabel` to `nameLabel`
- **Effort:** 5 minutes

### Task 1.2: Add Edit/Delete Case to Admin
- **File:** `adminDashboard.java`
- **Add:** Edit Case button → `EditCase.java` form
- **Add:** Delete Case button with confirmation
- **Effort:** 2-3 hours

---

## Phase 2: Core Feature Completion (Week 2)

### Task 2.1: Client-Case Association
- **Modify:** `client.java` - add `cases[]` field
- **Modify:** `AddCase.java` - link case to client username
- **Modify:** `dashBoard.java` - add "My Cases" button
- **New:** `ClientCaseView.java` - show cases for logged-in client

### Task 2.2: Budget Range Hiring Flow
- **New:** `BudgetRange.java` - client selects budget tier
- **New:** `LawyerFilter.java` - show lawyers in selected budget range
- **Modify:** `dashBoard.java` to include "Hire Lawyer" button

### Task 2.3: Admin Edit Lawyer
- **New:** `EditLawyer.java` - form to modify lawyer details
- **Modify:** `adminDashboard.java` - add Edit Lawyer button

---

## Phase 3: Security & Architecture (Week 3)

### Task 3.1: Password Hashing
- **Modify:** `users.java` - add password hashing on save
- **Modify:** `ClientLogin.java` - hash password on login
- **Library:** Use `java.security.MessageDigest` (SHA-256)

### Task 3.2: Configurable Admin Credentials
- Move from hardcoded to file-based config
- **New:** `config.txt` with admin credentials
- **Modify:** `Admin.java`/`AdminLogin.java` to read from config

### Task 3.3: Data Validation
- Add input validation to all forms
- Prevent SQL injection-style attacks on file storage

---

## Phase 4: Quality of Life (Week 4)

### Task 4.1: Case Search/Filter
- **New:** Search bar in `CaseInfoWindow.java`
- **Add:** Filter by status (Open/Closed/Pending)

### Task 4.2: Lawyer-Case Association
- **Modify:** `LawyerDashboard.java` - show assigned cases
- **Modify:** `cases.java` - track which lawyer owns which case

### Task 4.3: Case Status Updates
- **New:** Lawyer can update case status
- **Modify:** `cases.java` - updateCaseStatus() method

---

## Implementation Order (Recommended)

```
Week 1: Fixes
  - Fix lawyerDashboard bug
  - Add Edit/Delete to admin

Week 2: Core Features
  - Client-case linking
  - Budget hiring flow
  - Admin edit lawyer

Week 3: Security
  - Password hashing
  - Config file

Week 4: Polish
  - Search/filter
  - Lawyer case view
  - Status updates
```

---

## Estimated Total Effort
- **Phase 1:** 3 hours ✅ COMPLETED
- **Phase 2:** 8-10 hours ✅ COMPLETED
- **Phase 3:** 4-6 hours ✅ COMPLETED
- **Phase 4:** 6-8 hours ✅ COMPLETED
- **Total:** ~21-27 hours

---

## Current Implementation Status

### Completed Features
- [x] LawyerDashboard UI bug fix (userNameLabel → nameLabel)
- [x] Admin edit/delete case functionality (EditCase.java, DeleteCase.java)
- [x] Client view their cases (ClientCaseView.java)
- [x] Budget Range hiring flow (BudgetRange.java, LawyerFilter.java)
- [x] Password hashing with SHA-256 (PasswordUtils.java)
- [x] UserDAO with database integration (MySQL)
- [x] CaseDAO with full CRUD operations
- [x] New Case and User model classes
- [x] Config file for admin credentials (config.txt)

### Remaining Tasks
- [ ] LawyerFilter needs to show budget-specific filtering (currently shows all lawyers)
- [ ] Database connection requires MySQL/XAMPP setup
- [ ] Admin/Lawyer edit functionality needs enhancement
- [ ] Test with actual MySQL database