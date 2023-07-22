import React, { useState, useEffect } from "react";
import {
  Container,
  Row,
  Stack,
  Nav,
  Form,
  Col,
  ListGroup,
  Card,
  Button,
} from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const Board = () => {
  const [board, setBoard] = useState([]);
  const [boardId, setBoardId] = useState("");
  const [boardTitle, setBoardTitle] = useState("");
  const [searchInput, setSearchInput] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const selectedBoard = localStorage.getItem("selectedBoard");
    console.log(selectedBoard);
    if (selectedBoard) {
      setBoard(JSON.parse(selectedBoard));
      setBoardId(board.id);
      setBoardTitle(board.title);
      fetchBoardTasks();
    }
  }, [boardId]);

  const fetchBoardTasks = async () => {
    //To be implemented
  };

  const handleSearchInputChange = (event) => {
    setSearchInput(event.target.value);
  };

  const handleSearchSubmit = async (event) => {
    event.preventDefault();
    
    try {
      const response = await fetch(
        `http://localhost:8001/api/findTaskByIdOrTitle?title=${searchInput}`
      );

      if (response.ok) {
        console.log("Search successful. You can handle the data here.");
      } else {
        console.error("Failed to fetch task by ID or title");
      
      }
    } catch (error) {
      console.error("Failed to fetch task by ID or title:", error);
    
    }
  };

  const fetchBoardIdByTitle = async (boardTitle) => {
    try {
      const response = await fetch(
        `http://localhost:8001/api/findBoardIdByTitle?title=${boardTitle}`
      );

      if (response.ok) {
        const data = await response.json();
        setBoardId(data);
      } else {
        console.error("Failed to fetch board ID");
      }
    } catch (error) {
      console.error("Failed to fetch board ID:", error);
    }
  };

  const handleDeleteBoard = async (event) => {
    event.preventDefault();
    event.stopPropagation();

    try {
      console.log(JSON.stringify({ boardId }));
      const response = await fetch(
        `http://localhost:8001/api/deleteBoard?boardId=${boardId}`
      );

      if (response.ok) {
        console.log("Board deleted successfully");
      } else {
        console.error("Failed to delete board");
      }
    } catch (error) {
      console.error("Failed to delete board:", error);
    }
    // Navigate back to the home page
    navigate("/Pages/Workspace.js");
  };

  const handleBoardTitleChange = (event) => {
    setBoardTitle(event.target.value);
  };

  const handleBoardTitleSubmit = (event) => {
    event.preventDefault();
    console.log("Board Title:", boardTitle);
    fetchBoardIdByTitle(boardTitle);
  };

  const handleAddTaskClick = (column) => {
    localStorage.setItem("selectedColumn", column);
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
            <h3 style={{ paddingTop: 38 }}>Board Name</h3>
            <h6>Description</h6>
            <Stack className="mx-auto" direction="horizontal" gap={1}>
              {/* Input for search */}
              <Form onSubmit={handleSearchSubmit}>
                <Row className="align-items-center">
                  <Col xs="auto">
                    <Form.Label htmlFor="searchInput" visuallyHidden>
                      Search by Title
                    </Form.Label>
                    <Form.Control
                      type="text"
                      id="searchInput"
                      placeholder="Enter Title to search"
                      value={searchInput}
                      onChange={handleSearchInputChange}
                    />
                  </Col>
                  <Col xs="auto">
                    <Button variant="primary" type="submit">
                      Search
                    </Button>
                  </Col>
                </Row>
              </Form>
              </Stack>
            <br />
            <Row xs={1} md={3} className="g-4">
              <Col>
                <Card>
                  <Card.Header>To-Do</Card.Header>
                  <ListGroup variant="flush">
                    <ListGroup.Item>
                      <Button
                        variant="primary"
                        style={{ marginRight: 2 }}
                        onClick={() => handleAddTaskClick("To-Do")}
                      >
                        <Nav.Link href="../Pages/CreateTasks.js">
                          Add a task
                        </Nav.Link>
                      </Button>
                    </ListGroup.Item>
                  </ListGroup>
                </Card>
              </Col>
              <Col>
                <Card>
                  <Card.Header>Doing</Card.Header>
                  <ListGroup variant="flush">
                    <ListGroup.Item>
                      <Button
                        variant="primary"
                        style={{ marginRight: 2 }}
                        onClick={() => handleAddTaskClick("Doing")}
                      >
                        <Nav.Link href="../Pages/CreateTasks.js">
                          Add a task
                        </Nav.Link>
                      </Button>
                    </ListGroup.Item>
                  </ListGroup>
                </Card>
              </Col>
              <Col>
                <Card>
                  <Card.Header>Done</Card.Header>
                  <ListGroup variant="flush">
                    <ListGroup.Item>
                      <Button
                        variant="primary"
                        style={{ marginRight: 2 }}
                        onClick={() => handleAddTaskClick("Done")}
                      >
                        <Nav.Link href="../Pages/CreateTasks.js">
                          Add a task
                        </Nav.Link>
                      </Button>
                    </ListGroup.Item>
                  </ListGroup>
                </Card>
              </Col>
            </Row>
            <br />
            <Stack className="mx-auto" direction="horizontal" gap={1}>
              <Form onSubmit={handleBoardTitleSubmit}>
                <Row className="align-items-center">
                  <Col xs="auto">
                    <Form.Label htmlFor="boardTitle" visuallyHidden>
                      Board Title
                    </Form.Label>
                    <Form.Control
                      type="text"
                      id="boardTitle"
                      placeholder="Enter board title"
                      value={boardTitle}
                      onChange={handleBoardTitleChange}
                    />
                  </Col>
                  <Col xs="auto">
                    <Button variant="success" type="submit">
                      Get BoardId
                    </Button>
                  </Col>
                </Row>
              </Form>
              <div className="ms-auto" style={{ paddingBottom: 12 }}>
                <Button
                  variant="danger"
                  type="submit"
                  onClick={handleDeleteBoard}
                >
                  Delete board
                </Button>
              </div>
            </Stack>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default Board;
