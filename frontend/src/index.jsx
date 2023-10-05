import React from 'react';
import { ReactDOM, createRoot } from 'react-dom';
import { Route, BrowserRouter as Router } from 'react-router-dom';
import App from './App'; // Importe o componente App corretamente
import './assets';
import SeparationForm from './components/SeparationForm';
import './index.css';
import './styles';

// Importe o componente SeparationForm corretamente


const root = createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);


ReactDOM.render(
  <Router>
    <div>
      <Route exact path="/" component={App} />
      <Route path="/separations" component={SeparationForm} />
    </div>
  </Router>,
  document.getElementById('root')
);


