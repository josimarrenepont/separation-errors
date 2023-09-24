// routes.js
import { Route, BrowserRouter as Router, Switch } from 'react-router-dom';
import ErrosDeSeparacao from './components/ErrosDeSeparacao';
import SeparationForm from './components/SeparationForm';

function Routes() {
  return (
    <Router>
      <Switch>
        <Route path="/" exact component={ErrosDeSeparacao} />
        <Route path="/separation-form" component={SeparationForm} />
      </Switch>
    </Router>
  );
}

export default Routes;
