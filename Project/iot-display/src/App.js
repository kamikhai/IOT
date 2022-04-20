import React, { useEffect, useState } from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Typography } from "@mui/material";

import css from './App.module.css';

const fakeBackendResponse = [
  {
    "addedAt": "2022-04-19 12:30:55",
    "name": "Kamilla",
    "cardId": "123456757",
    "allowed": true
  },
  {
    "addedAt": "2022-04-19 12:30:55",
    "name": "Dmitry",
    "cardId": "123456731",
    "allowed": false
  }
]

export const App = () => {

  const [attendanceHistory, setAttendanceHistory] = useState(fakeBackendResponse);

  useEffect(() => {
    fetch("http://localhost:8080/api/history")
      .then((response) => response.json())
      .then((jsonResponse) => {
        setAttendanceHistory(jsonResponse);
      });
  }, [])

  return (
    <div className={css.pageContainer}>
      <Typography fontSize={ 22 }>{ "Учёт посещений" }</Typography>
      <TableContainer>
        <Table sx={ { minWidth: 650 } } >
          <TableHead>
            <TableRow>
              <TableCell>{"ID карточки"}</TableCell>
              <TableCell>{"Имя"}</TableCell>
              <TableCell>{"Время"}</TableCell>
              <TableCell align="right">{"Результат проверки доступа"}</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            { attendanceHistory.map((historyEntry) => {
              return (
                <TableRow>
                  <TableCell>{ historyEntry.cardId }</TableCell>
                  <TableCell>{ historyEntry.name }</TableCell>
                  <TableCell>{ historyEntry.addedAt }</TableCell>
                  <TableCell align="right">{ historyEntry.allowed ? "Доступ разрешен" : "Доступ запрещен"}</TableCell>
                </TableRow>
              )
            })}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
}