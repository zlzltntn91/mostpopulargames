import React, {useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import axios from "axios";
import {makeStyles} from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper'
import {Box, Button, Collapse, IconButton, Typography} from "@material-ui/core";
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';
import RankingRow from "./body/RankingRow";

const useStyles = makeStyles((theme) => ({
  root: {

    ...theme.typography.button,
    display: "flex",
    justifyContent: "center",
    padding: theme.spacing(1),
    fontWeight: "bold",
  },
  div: {
    display: "flex",
    justifyContent: "center",
  },
  table: {
    minWidth: 800,
    maxWidth: 800,
  },
  tableHeader: {
    fontWeight: "bold"
  },
  tr: {}
}));

const Ranking = () => {
    const [result, setResult] = useState(
      [{
        game_name: '',
        viewer_count: 0,
        streamers: [
          {
            game_id: '',
            game_name: '',
            id: '',
            language: '',
            started_at: '',
            thumbnail_url: '',
            title: '',
            type: '',
            user_id: '',
            user_login: '',
            user_name: '',
            viewer_count: '',
            tag_ids: '',
          }
        ]
      }]
    );

    async function ranking() {
      const response = await axios.get("http://localhost/games/ranking");
      setResult(response.data);
    }

    useEffect(() => {
      ranking();
    }, [])

    const classes = useStyles();

    return (
      <>
        <div>
          <div>
            <div className='headerStyle'>
              <p>MP.GG</p>
            </div>
          </div>
          <div className={classes.root}>
            <TableContainer component={Paper} className={classes.table}>
              <Table aria-label="collapsible table">
                <TableHead>
                </TableHead>
                <TableBody>
                  {result.slice(0, 10).map((v, idx) =>
                    <RankingRow key={idx} name={v.game_name} count={v.viewer_count} streamers={v.streamers}
                                rank={idx + 1}
                                top3={idx < 3 && 'no'}/>
                  )}
                </TableBody>
              </Table>
            </TableContainer>
          </div>
        </div>
      </>
    );
  }
;

Ranking.propTypes =
  {}
;

export default Ranking;
