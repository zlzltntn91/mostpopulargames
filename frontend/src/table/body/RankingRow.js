import React, {useEffect, useState} from "react";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import {Box, Collapse, createMuiTheme, IconButton, Typography} from "@material-ui/core";
import KeyboardArrowUpIcon from "@material-ui/icons/KeyboardArrowUp";
import KeyboardArrowDownIcon from "@material-ui/icons/KeyboardArrowDown";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import {makeStyles, ThemeProvider} from "@material-ui/core/styles";
import {orange} from "@material-ui/core/colors";
import GameInfo from "./GameInfo";


const theme = createMuiTheme({
  tr: {
    secondary: orange
  }
});

const useStyles = makeStyles((theme) => (
  {
    tr: {
      color: theme.tr
    }
  }
));


const RankingRow = (props) => {
  const {name, count, streamers, rank, top3} = props;
  const [open, setOpen] = useState(false);

  useEffect(() => {
  }, []);

  const classes = useStyles();

  function clickHandle() {
    setOpen(!open);
  }

  return (
    <>
      <TableRow style={{cursor: "pointer"}} className={top3 ? 'top3' : 'top100'} onClick={clickHandle}>
        <TableCell style={{padding: 5, width: 50}} align={'center'} className={'ranking'}>{rank}</TableCell>
        <TableCell style={{padding: 5, width: 500}} align={'left'} className={'game_name'}>{name}</TableCell>
        <TableCell style={{padding: 5, width: 50}} align={'center'}>
          <IconButton>
            {open ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
          </IconButton>
        </TableCell>
        <TableCell style={{padding: 5, width: 200}} align={'center'} className={'viewer_count'}>{count}</TableCell>
      </TableRow>
      <TableRow style={{width: 800}}>
        <GameInfo name={name} open={open}/>
      </TableRow>
    </>
  );
};

export default RankingRow