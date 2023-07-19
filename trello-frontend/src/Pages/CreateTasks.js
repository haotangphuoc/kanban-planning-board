import React, { useState, useRef} from "react";
import {
  Container,
  Row,
  Form,
  Col,
  Card,
  Button,
  Stack,
  Nav,
} from "react-bootstrap";

const CreateTasks = () => {
  const [validated, setValidated] = useState(false);
  const [title, setTitle] = useState("");
  const [date, setDate] = useState("");
  const [email, setEmail] = useState("");
  const dateInputRef = useRef(null);
  const [taskId, setTaskId] = useState("");



  const handleSubmit = async (event) => {
    event.preventDefault();
    event.stopPropagation();

    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      setValidated(true);
      return;
    }

    setValidated(true);

    const currentDate = new Date();
    const formattedStartDate = currentDate.toISOString().split("T")[0].replace(/-/g, "");
    const activeFlagFromLocalStorage = localStorage.getItem("selectedColumn");

    const taskData = {
      title: title,
      description: "test case",
      startDate: formattedStartDate,
      deadline: date,
      completionDate: date,
      activeFlag: activeFlagFromLocalStorage,
      list: {
        id: 1,
      },
    };

    try {
      console.log(JSON.stringify(taskData));
      const response = await fetch("http://localhost:8001/api/createTask", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(taskData),
      });

      if (response.ok) {
        console.log("Task created successfully");
        handleFetchTaskID(title);
        const data = await response;
      } else {
        console.error("Failed to create task");
      }
    } catch (error) {
      console.error("Failed to create task:", error);
    }
  };
  


  const handleFetchTaskID = async (title) => {
    try {
      const response = await fetch(
        `http://localhost:8001/api/findTaskByIdOrTitle?title=${title}`
      );
      const responseData = await response.json();
  
      if (response.ok && responseData.id) {
        const taskID = responseData.id;
        console.log("Task ID:", taskID);
        setTaskId(taskID); 
      } else {
        console.error("Failed to fetch task ID");
      }
    } catch (error) {
      console.error("Failed to fetch task ID:", error);
    }
  };


  const handleSubmitMember = async (event) => {
    event.preventDefault();
    event.stopPropagation();

    const memberData = {
      id: taskId,
      users: [
        {
          email: email,
        },
      ],
    };

    try {
      console.log(JSON.stringify(memberData));
      const response = await fetch("http://localhost:8001/api/assignMembersToTask", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(memberData),
      });

      if (response.ok) {
        console.log("Member added to task successfully");
        // Handle the response or update the state as needed
      } else {
        console.error("Failed to add member to task");
        // Handle the error appropriately
      }
    } catch (error) {
      console.error("Failed to add member to task:", error);
      // Handle network errors or other exceptions
    }
  };

  const handleTitleChange = (e) => {
    setTitle(e.target.value);
  };

  const handleDateChange = (e) => {
    setDate(e.target.value);
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  return (
    <div style={{ minHeight: "93vh" }}>
      <Container>
        <Row>
          <div>
            <Stack
              className="mx-auto"
              direction="horizontal"
              style={{ paddingTop: 38 }}
            >
              <div style={{ paddingBottom: 12 }}>
                <Card.Title>
                  <Nav.Link href="../Pages/Board.js">&#60; Back</Nav.Link>
                </Card.Title>
              </div>
            </Stack>
            <h2 style={{ paddingTop: 38, paddingBottom: 38 }}>Create a Task</h2>
            <Card>
              <Card.Body>
                <Form noValidate validated={validated} onSubmit={handleSubmit}>
                  <Row className="mb-3">
                    <Form.Group as={Col} md="4" controlId="validationCustom01">
                      <Form.Label>Task name</Form.Label>
                      <Form.Control
                        required
                        type="text"
                        placeholder="Enter a task name"
                        value={title}
                        onChange={handleTitleChange}
                      />
                      <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                      <Form.Control.Feedback type="invalid">
                        Please enter a task name.
                      </Form.Control.Feedback>
                    </Form.Group>
                  </Row>
                  <Row className="mb-3">
                    <Form.Group as={Col} controlId="validationCustom02">
                      <Form.Label>Due Date: </Form.Label>
                      <input
                        style={{ marginLeft: 15 }}
                        type="date"
                        onChange={handleDateChange}
                        ref={dateInputRef}
                        value={date}
                        required
                      />
                    </Form.Group>
                  </Row>
                  <Button variant="primary" type="submit">
                    Create
                  </Button>
                </Form>
              </Card.Body>
            </Card>

            <h2 style={{ paddingTop: 38, paddingBottom: 38 }}>Assign to member</h2>
            <Card>
              <Card.Body>
                <Form noValidate validated={validated} onSubmit={handleSubmitMember}>
                  <Row className="mb-3">
                    <Form.Group as={Col} controlId="validationCustom02">
                      <Form.Control
                        required
                        type="email"
                        placeholder="Enter an email"
                        value={email}
                        onChange={handleEmailChange}
                      />
                    </Form.Group>
                  </Row>
                  <Button variant="primary" type="submit">
                    Add member
                  </Button>
                </Form>
              </Card.Body>
            </Card>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default CreateTasks;
