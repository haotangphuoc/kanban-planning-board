import React, { useEffect, useState } from "react";
import { Container, Row, Nav, Stack, Card } from "react-bootstrap";

const Workspace = () => {
  const [workspace, setWorkspace] = useState([]);
  const [workspaceId, setWorkspaceId] = useState("");
  const [workspaceName, setWorkspaceName] = useState("");
  const [workspaceDescription, setWorkspaceDescription] = useState("");
  const [boards, setBoards] = useState([]);

  useEffect(() => {
    const selectedWorkspace = localStorage.getItem("selectedWorkspace");
    console.log(selectedWorkspace);
    if (selectedWorkspace) {
      setWorkspace(JSON.parse(selectedWorkspace));
      setWorkspaceId(workspace.id);
      setWorkspaceName(workspace.name);
      setWorkspaceDescription(workspace.description);
      fetchWorkspaceBoards();
    }
  }, [workspaceId]);

  const fetchWorkspaceBoards = async () => {
    try {
      const response = await fetch(
          `http://localhost:8001/api/getWorkspaceBoards?id=${workspaceId}`
      );
      const data = await response.json();

      if (response.ok) {
        setBoards(data);
      } else {
        console.error("Failed to fetch workspace's boards");
        // Handle error case
      }
    } catch (error) {
      console.error("Failed to fetch workspace's boards", error);
      // Handle error case
    }
  };

  return (
      <div style={{ minHeight: "93vh" }}>
        <Container>
          <Row>
            <Stack
                className="mx-auto"
                direction="horizontal"
                style={{ paddingTop: 38 }}
            >
              <div style={{ paddingBottom: 12 }}>
                <Card.Title>
                  <Nav.Link href="../Pages/Homepage.js">&#60; Homepage</Nav.Link>
                </Card.Title>
              </div>
            </Stack>
            <div>
              <h2 style={{ paddingTop: 38 }}>Workspaces</h2>
              <br />
              <h4>{workspaceName}</h4>
              <p>{workspaceDescription}</p>

              <br />
              <Card>
                <Card.Header>
                  <Nav variant="tabs" defaultActiveKey="#boards">
                    <Nav.Item>
                      <Nav.Link href="#boards">Boards</Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                      <Nav.Link href="../Pages/CreateBoards.js">
                        Create a board
                      </Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                      <Nav.Link href="../Pages/Members.js">Members</Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                      <Nav.Link href="../Pages/Settings.js">Settings</Nav.Link>
                    </Nav.Item>
                  </Nav>
                </Card.Header>
                <Card.Body>
                  {boards.map((board) => (
                      <div key={board.id}>
                        <Card.Title>
                          <Nav.Link
                              href={`../Pages/Board.js?name=${board.title}`}
                              onClick={() => {
                                localStorage.setItem(
                                    "selectedBoard",
                                    JSON.stringify(board)
                                );
                              }}
                          >
                            {board.title}
                          </Nav.Link>
                      </Card.Title>
                      </div>
                  ))}
                </Card.Body>
              </Card>
            </div>
          </Row>
        </Container>
      </div>
  );
};

export default Workspace;
