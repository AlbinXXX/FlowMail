
# Email Validation Application

A Spring Boot MVC application that generates and validates email permutations based on user-provided first name, last name, and domain. The application checks the validity of email domains using MX record verification and provides real-time progress updates via Server-Sent Events (SSE).

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Email Permutation Generation**: Generates various email formats based on the user's first name, last name, and domain.
- **MX Record Verification**: Validates email domains by checking their MX records to ensure they can receive emails.
- **Real-Time Progress Updates**: Utilizes Server-Sent Events (SSE) to provide live feedback on the email validation process.
- **Data Persistence**: Stores validated email formats in an H2 in-memory database.
- **User-Friendly Interface**: Clean and intuitive UI built with Thymeleaf templates.
- **Asynchronous Processing**: Handles email validations asynchronously to ensure a responsive user experience.

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring MVC**
- **Thymeleaf**
- **H2 Database**
- **MailHog**
- **Maven**
- **Lombok**
- **Server-Sent Events (SSE)**

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java Development Kit (JDK) 17** or higher installed. [Download JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- **Maven** installed. [Download Maven](https://maven.apache.org/download.cgi)
- **MailHog** installed and running for capturing outgoing emails during development. [MailHog Installation Guide](https://github.com/mailhog/MailHog#installation)
- **Git** installed (optional, for cloning the repository).

## Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/your-username/email-validation-app.git
   cd email-validation-app
   ```
   Replace your-username with your actual GitHub username if applicable.

2. **Install Dependencies**

   Ensure Maven is installed and configured correctly. Navigate to the project's root directory and run:

   ```bash
   mvn clean install
   ```
   This command will compile the project and download all necessary dependencies.

## Running the Application

1. **Start MailHog**

   MailHog captures outgoing emails sent by the application. Ensure MailHog is running before starting the Spring Boot application.

   **Windows:**
   ```bash
   MailHog.exe
   ```
   **macOS/Linux:**
   ```bash
   ./MailHog
   ```
   Access MailHog's web interface at http://localhost:8025.

2. **Run the Spring Boot Application**

   Navigate to the project's root directory and execute:

   ```bash
   mvn spring-boot:run
   ```
   Alternatively, you can run the FlowFormApplication.java class directly from your IDE.

3. **Access the Application**

   Open your web browser and navigate to http://localhost:8080/.

## Testing

1. **Submit the Email Validation Form**
   - Domain: thepworld.com
   - First Name: albin
   - Last Name: rushiti
   - Click the "Find Emails" button to initiate the email validation process.

2. **Observe Real-Time Progress**
   - **Progress Bar**: Displays the percentage of emails processed.
   - **Log Entries**: Shows each email being checked along with validation messages.
   - **Found Emails**: Lists all emails that passed the MX record validation.

3. **Verify Emails in MailHog**
   - Visit http://localhost:8025 to view the captured emails. Only the emails with valid MX records should appear here.

4. **Check Stored Email Formats in H2 Console**
   - Access H2 Console: http://localhost:8080/h2-console
   - JDBC URL: jdbc:h2:mem:emaildb
   - Username: sa
   - Password: (leave blank)
   - Execute the following SQL query to view stored email formats:
     ```sql
     SELECT * FROM email_formats;
     ```

## Project Structure

```
email-validation-app/
├── src/
│   ├── main/
│   │   ├── java/com/flowform/FlowForm/
│   │   │   ├── config/
│   │   │   │   └── AsyncConfig.java
│   │   │   ├── controller/
│   │   │   │   └── EmailController.java
│   │   │   ├── model/
│   │   │   │   ├── EmailFormat.java
│   │   │   │   ├── EmailValidationResult.java
│   │   │   │   ├── ProgressUpdate.java
│   │   │   │   └── UserInput.java
│   │   │   ├── repository/
│   │   │   │   └── EmailFormatRepository.java
│   │   │   └── service/
│   │   │       ├── EmailService.java
│   │   │       └── EmailValidationService.java
│   │   ├── resources/
│   │   │   ├── static/
│   │   │   │   ├── css/
│   │   │   │   │   └── styles.css
│   │   │   │   └── js/
│   │   │   │       └── storage.js
│   │   │   ├── templates/
│   │   │   │   ├── formats.html
│   │   │   │   ├── index.html
│   │   │   │   └── result.html
│   │   │   └── application.properties
│   └── test/
│       └── java/com/flowform/FlowForm/
│           └── // Your test classes
├── pom.xml
└── README.md
```

## Usage

1. **Navigate to the Home Page**

   Open http://localhost:8080/ in your web browser to access the email input form.

2. **Fill Out the Form**

   - Domain: Enter the domain you want to search for (e.g., thepworld.com).
   - First Name: Enter the first name (e.g., albin).
   - Last Name: Enter the last name (e.g., rushiti).

3. **Submit the Form**

   Click "Find Emails" to start the validation process. You'll be redirected to the results page displaying real-time progress and the list of valid emails found.

4. **View Captured Emails in MailHog**

   Visit http://localhost:8025 to view the emails that have been validated and captured by MailHog.

5. **View Stored Email Formats**

   Access the H2 console at http://localhost:8080/h2-console to view and manage the stored email formats.

## Screenshots



## License

This project is licensed under the MIT License.

Happy Coding!
