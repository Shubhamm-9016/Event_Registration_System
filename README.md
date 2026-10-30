# EventSpot — Event Registration System

A beginner-friendly AIP mini project built with Java 17, JSP, Servlets, JDBC, MySQL, Maven and Apache Tomcat 11.0.24. It has dynamic event cards with images, event registration, a success page showing the generated registration ID, and a page listing all registrations. There is no login or admin panel.

## Included features
- Responsive image-based event cards populated from MySQL.
- Registration form with browser-side and server-side validation.
- Prepared statements and generated registration IDs.
- Registrations table joined with event details.
- SVG sample illustrations are included so the cards have images immediately; you can replace them with your own photos.

## Requirements
- JDK 17
- Eclipse IDE for Enterprise Java and Web Developers
- MySQL Server and MySQL Workbench
- Apache Tomcat 11.0.24
- Maven (Eclipse can resolve Maven dependencies)

## 1. Create the database
1. Start MySQL Server and open MySQL Workbench.
2. Open `database.sql` from this project.
3. Execute the script. It creates `event_registration_db`, both tables, and three sample events. The sample inserts use `WHERE NOT EXISTS` to avoid inserting those named events repeatedly.
4. Run `SELECT * FROM events;` and `SELECT * FROM `register`;` to verify the tables.

## 2. Configure the database password
Open `src/main/java/com/event/DBConnection.java` and replace:

```java
private static final String PASSWORD = "YOUR_MYSQL_PASSWORD";
```

with the password for your local MySQL user. The default username is `root`; change `USER` too if you use a different account. Do not share a project copy that contains your real password.

## 3. Import into Eclipse
1. Extract the ZIP to a folder.
2. In Eclipse choose **File → Import → Maven → Existing Maven Projects**.
3. Browse to the extracted `EventRegistrationSystem` folder and select `pom.xml`.
4. Finish and wait for Maven to download dependencies. If needed, right-click the project → **Maven → Update Project**.
5. Set the project JRE to Java 17: **Properties → Java Build Path**.
6. Add Apache Tomcat 11.0.24 in **Window → Show View → Servers** (or the Servers view), create a new server, and select your Tomcat 11 installation directory.
7. Add the project to the server and start it.

## 4. Run and test
Open:

`http://localhost:8080/EventRegistrationSystem/events`

If Eclipse deploys the app under a different context root, use that context root instead. You can also right-click the project and choose **Run As → Run on Server**.

Test these actions:
1. Check that all three event cards appear with images.
2. Select **Register Now** on an event.
3. Submit a name, valid email and phone number.
4. Confirm the success page shows a registration ID.
5. Select **View All Registrations** and confirm the new row appears.
6. Verify the records in MySQL Workbench with `SELECT * FROM `register`;`.

## Troubleshooting
- **Access denied for user:** check `USER` and `PASSWORD` in `DBConnection.java`.
- **Communications link failure:** start MySQL Server and check that it listens on port 3306.
- **Unknown database:** execute `database.sql` in MySQL Workbench.
- **No events shown:** check `SELECT * FROM events;` and the Eclipse/Tomcat logs.
- **404 error:** check the deployed context root and use `/events` at the end of the URL.
- **Servlet package errors:** this project targets Tomcat 11.0.24 and `jakarta.servlet`. Do not deploy it to Tomcat 9 without migrating to `javax.servlet`.
- **Date issue:** sample events are dated in November 2026. Update dates in the `events` table if you run the project later.

## Project structure
```text
EventRegistrationSystem/
├── database.sql
├── pom.xml
├── README.md
└── src/main/
    ├── java/com/event/
    │   ├── DBConnection.java
    │   ├── EventServlet.java
    │   ├── RegisterServlet.java
    │   └── ViewRegistrationsServlet.java
    └── webapp/
        ├── index.jsp
        ├── register.jsp
        ├── success.jsp
        ├── registrations.jsp
        ├── css/style.css
        ├── images/
        └── WEB-INF/web.xml
```
