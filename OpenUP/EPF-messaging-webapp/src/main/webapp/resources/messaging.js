'use strict';

const collector = React.createElement;

class MessagingCollector extends React.Component {
  constructor(props) {
    super(props);
    this.state = { count: false, messages: [] };
  }

  render() {
    return collector(
      'div',
      {},
      'Like'
    );
  }
}

const domContainer = document.querySelector('#messaging');
const root = ReactDOM.createRoot(domContainer);
root.render(collector(MessagingCollector));