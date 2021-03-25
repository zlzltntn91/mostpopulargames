import React, {useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import axios from "axios";
import TableCell from "@material-ui/core/TableCell";
import {Box, Collapse, Typography} from "@material-ui/core";
import TableRow from "@material-ui/core/TableRow";

const GameInfo = props => {
  const {name, open} = props;

  const [game, setGame] = useState({
    id: '',
    name: '',
    platforms: [],
    platformsName: [],
  });

  const gameInfo = async () => {
    const result = await axios.get(`http://localhost/games/${name}`);
    setGame(result.data);
  }

  useEffect(() => {
    gameInfo();
  }, []);

  useEffect(() => {
    const arr = [];
    console.log(typeof game.platformsName);
  })
  return (
    <TableCell colSpan={4} style={{paddingBottom: 0, paddingTop: 0, border: "none"}}>
      <Collapse in={open} timeout={"auto"} unmountOnExit>
        <Box margin={3} paddingLeft={3} paddingRight={3}>
          <Typography variant="h4" gutterBottom component="div" style={{color: "#f50057"}}>
            {name}
          </Typography>
          <Typography variant={"h6"} align="right">
            {game.platformsName.join(", ")}
          </Typography>
        </Box>
      </Collapse>
    </TableCell>
  );
};

GameInfo.propTypes = {};

export default GameInfo;

