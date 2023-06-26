import React, { useState, useEffect } from "react";
import {
  Container,
  Row,
  Form,
  Col,
  Card,
  Button,
  Stack,
  Nav,
  ListGroup,
} from "react-bootstrap";

const Members = () => {
  const [workspaceId, setWorkspaceId] = useState("");
  const [email2, setEmail2] = useState("");

  useEffect(() => {
    const workspaceName = localStorage.getItem("workspaceName");
    if (workspaceName) {
      findWorkspaceIdByName(workspaceName);
    }
  }, []);

  const findWorkspaceIdByName = async (workspaceName) => {
    try {
      const response = await fetch(`http://localhost:8001/api/findWorkspaceIdByName?name=${workspaceName}`);
      const data = await response.json();

      if (response.ok && data >= 0) {
        setWorkspaceId(data);
      } else {
        console.error("Failed to fetch workspace ID");
        // Handle error case
      }
    } catch (error) {
      console.error("Failed to fetch workspace ID:", error);
      // Handle error case
    }
  };
  const Email1 = localStorage.getItem("userData");
  const email1Data = JSON.parse(Email1);
  const email1 = email1Data.email;

  const handleInviteSubmit = async () => {
    const addMemberFunction = {
      id: workspaceId,
      Users: [
        {
          email1:email1,
          email2:email2,
        },
      ],
    };
    // Call the API to add members to workspace
    try {
      console.log(JSON.stringify(addMemberFunction))
      const response = await fetch("http://localhost:8001/api/addMembersToWorkspace",{
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Methods": "POST,PATCH,OPTION",
          },
          body: JSON.stringify(addMemberFunction),
        }
      );

      if (response.ok) {
        console.log("Members added successfully");
      } else {
        console.error("Failed to add members");
        // Handle error case
      }
    } catch (error) {
      console.error("Failed to add members:", error);
      // Handle error case
    }
  };


  const handleWorkspaceAddMember = (event) => {
    setEmail2(event.target.value);
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
                  <Nav.Link href="../Pages/Workspace.js">
                    &#60; Workspace
                  </Nav.Link>
                </Card.Title>
              </div>
            </Stack>
            <h2 style={{ paddingTop: 38, paddingBottom: 38 }}>
              Workspace Members
            </h2>
            <Card>
              <Card.Body>
                <Form>
                  <Row className="mb-3">
                    <Form.Group as={Col} md="4" controlId="validationCustom01">
                      <Form.Label>Invite members to your workspace</Form.Label>
                      <Form.Control
                        required
                        type="email"
                        name="email2"
                        placeholder="Enter email"
                        value={email2}
                        onChange={handleWorkspaceAddMember}
                      />
                    </Form.Group>
                  </Row>
                  <Button
                    variant="primary"
                    type="submit"
                    as={Col}
                    md="2"
                    onClick={handleInviteSubmit}
                  >
                    Invite
                  </Button>
                </Form>
                <br />
                <h6>Current Members</h6>
                <ListGroup variant="flush">
                  <ListGroup.Item disabled>
                    Member Name 1 | email@dal.ca
                  </ListGroup.Item>
                  <ListGroup.Item>
                    Member Name 2 | email@dal.ca{" "}
                    <Button variant="danger" style={{ marginLeft: 25 }}>
                      Remove
                    </Button>
                  </ListGroup.Item>
                  <ListGroup.Item>
                    Member Name 3 | email@dal.ca{" "}
                    <Button variant="danger" style={{ marginLeft: 25 }}>
                      Remove
                    </Button>
                  </ListGroup.Item>
                </ListGroup>
              </Card.Body>
            </Card>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default Members;
