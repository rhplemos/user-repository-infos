# GitHub User and Repository Information App

This is a simple Android application that allows users to search for GitHub users and retrieve information about their repositories. The app is built using the MVVM architecture pattern and utilizes various technologies such as Koin, Retrofit, Mockito, and JUnit.

## Features

- Search for GitHub users by their username.
- View detailed information about the user, including their name, bio, location, number of public repositories, and company.
- Retrieve a list of repositories owned by the user, including the repository name, description, and number of stars.

## Technologies Used

- **MVVM (Model-View-ViewModel):** The app follows the MVVM architecture pattern, which separates the user interface (View) from the business logic (ViewModel) and data handling (Model) layers. This architecture promotes separation of concerns and testability.

- **Koin:** Dependency injection framework used to manage the app's dependencies. Koin simplifies the process of providing and injecting dependencies into the different components of the app.

- **Retrofit:** A type-safe HTTP client for Android used to make API requests to the GitHub API. Retrofit simplifies the process of handling network requests and provides automatic JSON parsing.

- **Mockito:** A mocking framework used for unit testing. Mockito allows the creation of mock objects to simulate dependencies and behavior during testing. It helps ensure the reliability and correctness of the app's business logic.

- **JUnit:** A unit testing framework used for writing and executing tests. JUnit provides a simple and flexible framework for testing individual units of code, such as methods or functions, in isolation.

## Getting Started

To get started with the app, follow these steps:

1. Clone the repository: `git clone https://github.com/your-username/your-repo.git`
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

## How to Use the App

1. Launch the app on your Android device or emulator.
2. Choose whether you want to fetch a repository or a user
3. Enter the username of a GitHub user or a repository name in the search bar.
4. Tap the search button to initiate the search.
5. The app will display the user's information, including their name, bio, location, number of public repositories, and company.
6. Scroll down to view the user's repositories. Each repository will show its name, description, and number of stars.
