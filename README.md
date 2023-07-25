# Group 18

## Links

#### GitLab Repo URL: https://git.cs.dal.ca/courses/2023-summer/csci-3130/group18

#### Branches

- Release 1: https://git.cs.dal.ca/courses/2023-summer/csci-3130/group18/-/tree/main?ref_type=heads
- Release 2: https://git.cs.dal.ca/courses/2023-summer/csci-3130/group18/-/tree/release-2?ref_type=heads
- Release 3: https://git.cs.dal.ca/courses/2023-summer/csci-3130/group18/-/tree/release-3?ref_type=heads

The **release-3** branch contains the most recent and updated code.

## Members

- Ayah Abaza
- Eric Wu
- Hongye Shan
- Shafay Zulfiqar
- Hao Tang

## Commands

Ensure you have **Node.js** and **Apache Maven** installed on your system.

- **Apache Maven:** https://maven.apache.org/install.html
- **Node.js:** https://nodejs.org/en/download
  - Helpful link: https://phoenixnap.com/kb/install-node-js-npm-on-windows

### Frontend

Change directories to the frontend folder by running the command `cd trello-frontend` and run `npm install`. Once everything is downloaded, run `npm start` to open the project.

### Backend

**Ensure you are connected to the Dal network.**
Navigate to the following file "DemoTrelloBackendApplication.java" found in: Backend-Trello\src\main\java\Group18\Demo\Trello\DemoTrelloBackendApplication.java and run the public class DemoTrelloBackendApplication.

### Tests

Please note that the CI pipieline in the GitLab repo contains the builds and tests for every commit. However, you may manually run the tests through the terminal.

1. Navigate to the backend folder

```
cd Backend-Trello
```

2. Run the tests

```
mvn clean test
```
