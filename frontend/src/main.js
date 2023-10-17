import React from 'react';
import ReactDOM from 'react-dom';
import App from './App'; // Supondo que você queira importar o componente App
import './ErrosDeSeparacao';
import './SeparationForm';
import './index.css';
import './index.html';
import './index.jsx';
import './separationForm.css';
ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);