import React from 'react';
import { ReactDOM, createRoot } from 'react-dom';
import { Route, BrowserRouter as Router } from 'react-router-dom';
import App from './App'; // Importe o componente App corretamente
import SeparationForm from './components/SeparationForm';

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
      <Route path="/separation" component={SeparationForm} />
    </div>
  </Router>,
  document.getElementById('root')
);


