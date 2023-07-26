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
  const navigate = useNavigate();

  const [board, setBoard] = useState([]);
  const [boardId, setBoardId] = useState("");
  const [boardTitle, setBoardTitle] = useState("");
  const [searchInput, setSearchInput] = useState("");
  const [boardLists, setBoardLists] = useState([]);
  const [filteredTasks, setFilteredTasks] = useState([]);
  const [selectedFilter, setSelectedFilter] = useState("");
  const [searchResult, setSearchResult] = useState(null);

  const selectedBoard = localStorage.getItem("selectedBoard");
  const selectedWorkspace = localStorage.getItem("selectedWorkspace");
  const [workspaceId, setWorkspaceId] = useState("");
  const [workspaceName, setWorkspaceName] = useState("");

  useEffect(() => {
    if (selectedBoard) {
      setBoard(JSON.parse(selectedBoard));
      setBoardId(board.id);
      setBoardTitle(board.title);
      fetchBoardLists();
    }
  }, [boardId]);

  useEffect(() => {
    if (selectedFilter === "") {
      setFilteredTasks(boardLists);
    } else {
      filterTasksByDueDate(selectedFilter);
    }
  }, [selectedFilter, boardLists]);

  const fetchBoardLists = async () => {
    try {
      const response = await fetch(
          `http://localhost:8001/api/getBoardLists?id=${boardId}`
      );
      const data = await response.json();

      if (response.ok) {
        setBoardLists(data);
        setFilteredTasks(data.flatMap((list) => list.tasks));
      } else {
        console.error("Failed to fetch board's lists");
        // Handle error case
      }
    } catch (error) {
      console.error("Failed to fetch board's lists", error);
      // Handle error case
    }
  };

  const handleSearchInputChange = (event) => {
    setSearchInput(event.target.value);
  };

    const handleSearchSubmit = (event) => {
        event.preventDefault();

        const filteredTasks = boardLists.flatMap((list) => {
            const filteredList = {
                ...list,
                tasks: list.tasks.filter(
                    (task) =>
                        task.title.toLowerCase().includes(searchInput.toLowerCase())
                ),
            };
            return filteredList;
        });

        setSearchResult(filteredTasks);
    };


  const filterTasksByDueDate = (filter) => {
    const currentDate = new Date();
    const filteredLists = boardLists.map((list) => {
      const filteredTasks = list.tasks.filter((task) => {
        const taskDueDate = new Date(task.deadline);
        if (filter === "Due today") {
          return taskDueDate.toISOString().split("T")[0] === currentDate.toISOString().split("T")[0];
        } else if (filter === "Due this week") {
          const endOfWeek = new Date();
          endOfWeek.setDate(currentDate.getDate() + 7);
          return taskDueDate >= currentDate && taskDueDate <= endOfWeek;
        } else if (filter === "Overdue") {
          return taskDueDate < currentDate && taskDueDate.toISOString().split("T")[0] !== currentDate.toISOString().split("T")[0];
        }
        return true;
      });
      return { ...list, tasks: filteredTasks };
    });
    setFilteredTasks(filteredLists);
  };



  const handleFilterChange = (filter) => {
    setSelectedFilter(filter);
    filterTasksByDueDate(filter);
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

  const handleAddTaskClick = (list) => {
    console.log(list);
    localStorage.setItem("selectedList", JSON.stringify(list));
    navigate("../Pages/CreateTasks.js");
  };

  const handleClearSearch = () => {
    setSearchInput("");
    setSearchResult(null);
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
                    <Nav.Link
                        href={`../Pages/Workspace.js?name=${workspaceName}`}
                    >
                      &#60; Workspace
                    </Nav.Link>
                  </Card.Title>
                </div>
              </Stack>
              <h3 style={{ paddingTop: 38 }}>{boardTitle}</h3>
              <br />
              <Stack
                  className="mx-auto"
                  direction="horizontal"
                  gap={1}
                  style={{ justifyContent: "space-between" }}
              >
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
                      <Button variant="primary" type="submit" style={{ marginRight: "8px" }}>
                        Search
                      </Button>
                      {searchResult && (
                          <Button
                              variant="outline-primary"
                              onClick={handleClearSearch}
                          >
                            Clear Search
                          </Button>
                      )}
                    </Col>
                  </Row>
                </Form>
                <Stack className="filter-options" direction="horizontal" gap={1}>
                  <Button
                      variant="outline-primary"
                      onClick={() => handleFilterChange("Due today")}
                      active={selectedFilter === "Due today"}
                  >
                    Due today
                  </Button>
                  <Button
                      variant="outline-primary"
                      onClick={() => handleFilterChange("Due this week")}
                      active={selectedFilter === "Due this week"}
                  >
                    Due this week
                  </Button>
                  <Button
                      variant="outline-primary"
                      onClick={() => handleFilterChange("Overdue")}
                      active={selectedFilter === "Overdue"}
                  >
                    Overdue
                  </Button>
                  <Button
                      variant={selectedFilter === "" ? "primary" : "outline-primary"}
                      onClick={() => handleFilterChange("")}
                  >
                    Show all tasks
                  </Button>
                </Stack>
              </Stack>
              <br />
              {searchResult ? (
                  <>
                    <h3>Search Results</h3>
                    <Row xs={1} md={3} className="g-4">
                      {searchResult.length > 0 ? (
                          searchResult.map((list, index) => (
                              <Col key={index}>
                                <Card>
                                  <Card.Header>{list.status}</Card.Header>
                                  <Card.Body>
                                    {list.tasks &&
                                        list.tasks.map((task) => (
                                            <div key={task.id} style={{ paddingBottom: 12 }}>
                                              <Card.Title>{task.title}</Card.Title>
                                              Start date: {task.startDate}
                                              <br></br>
                                              Deadline: {task.deadline}
                                              <br></br>
                                              Member:
                                            </div>
                                        ))}
                                  </Card.Body>
                                  <ListGroup variant="flush">
                                    <ListGroup.Item>
                                      <Button
                                          variant="primary"
                                          style={{ marginRight: 2 }}
                                          onClick={() => handleAddTaskClick(list)}
                                      >
                                        <Nav.Link href="../Pages/CreateTasks.js">
                                          Add a task
                                        </Nav.Link>
                                      </Button>
                                    </ListGroup.Item>
                                  </ListGroup>
                                </Card>
                              </Col>
                          ))
                      ) : (
                          <Col>
                            <p>Search result not found.</p>
                          </Col>
                      )}
                    </Row>
                  </>
              ) : (
                  <Row xs={1} md={3} className="g-4">
                    {selectedFilter === ""
                        ? boardLists.map((list, index) => (
                            <Col key={index}>
                              <Card>
                                <Card.Header>{list.status}</Card.Header>
                                <Card.Body>
                                  {list.tasks &&
                                      list.tasks.map((task) => (
                                          <div
                                              key={task.id}
                                              style={{ paddingBottom: 12 }}
                                          >
                                            <Card.Title>{task.title}</Card.Title>
                                            Start date: {task.startDate}
                                            <br></br>
                                            Deadline: {task.deadline}
                                            <br></br>
                                            Member:
                                          </div>
                                      ))}
                                </Card.Body>
                                <ListGroup variant="flush">
                                  <ListGroup.Item>
                                    <Button
                                        variant="primary"
                                        style={{ marginRight: 2 }}
                                        onClick={() => handleAddTaskClick(list)}
                                    >
                                      <Nav.Link href="../Pages/CreateTasks.js">
                                        Add a task
                                      </Nav.Link>
                                    </Button>
                                  </ListGroup.Item>
                                </ListGroup>
                              </Card>
                            </Col>
                        ))
                        : filteredTasks.map((list, index) => (
                            <Col key={index}>
                              <Card>
                                <Card.Header>{list.status}</Card.Header>
                                <Card.Body>
                                  {list.tasks &&
                                      list.tasks.map((task) => (
                                          <div
                                              key={task.id}
                                              style={{ paddingBottom: 12 }}
                                          >
                                            <Card.Title>{task.title}</Card.Title>
                                            Start date: {task.startDate}
                                            <br></br>
                                            Deadline: {task.deadline}
                                            <br></br>
                                            Member:
                                          </div>
                                      ))}
                                </Card.Body>
                                <ListGroup variant="flush">
                                  <ListGroup.Item>
                                    <Button
                                        variant="primary"
                                        style={{ marginRight: 2 }}
                                        onClick={() => handleAddTaskClick(list)}
                                    >
                                      <Nav.Link href="../Pages/CreateTasks.js">
                                        Add a task
                                      </Nav.Link>
                                    </Button>
                                  </ListGroup.Item>
                                </ListGroup>
                              </Card>
                            </Col>
                        ))}
                  </Row>
              )}
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




