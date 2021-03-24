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
import {Button} from "@material-ui/core";

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
    width: 600,
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

  const tableHeader =
    <TableRow className={classes.tableHeader}>
      <TableCell style={{padding: 7}} align={'center'}>Rank</TableCell>
      <TableCell style={{padding: 7}} align={'center'}>Game</TableCell>
      <TableCell style={{padding: 7}} align={'center'}>Viewer</TableCell>
    </TableRow>;

  const tableBody = result.map((v, index) =>
    <TableRow key={index} style={{padding: 1}} className={index < 3 && 'top3'}>
      <TableCell style={{padding: 5}} align={'center'} className={'ranking'}
                 id={index < 3 && 'top3'}>{index + 1}</TableCell>
      <TableCell style={{padding: 5}} align={'left'} className={'game_name'}>{v.game_name || '조회중입니다.'}</TableCell>
      <TableCell style={{padding: 5}} align={'center'} className={'viewer_count'}>{v.viewer_count || ''}</TableCell>
    </TableRow>
  )
  return (
    <>
      <div>
        <div>
          <div className='headerStyle'>
            <p>MP.GG</p>
          </div>
        </div>
        <Button/>
        <div className={classes.root}>
          <TableContainer component={Paper} className={classes.table}>
            <Table>
              <TableHead>
                {/*{tableHeader}*/}
              </TableHead>
              <TableBody>
                {tableBody}
              </TableBody>
            </Table>
          </TableContainer>
        </div>
      </div>
    </>
  );
};

Ranking.propTypes = {};

export default Ranking;
