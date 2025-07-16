// Tab switching logic
document.querySelectorAll('#tabs .tab').forEach(tab => {
  tab.addEventListener('click', () => {
    document.querySelectorAll('#tabs .tab').forEach(t => t.classList.remove('active'));
    document.querySelectorAll('#content > div').forEach(c => c.classList.remove('active'));

    tab.classList.add('active');
    document.getElementById(tab.getAttribute('data-tab')).classList.add('active');
  });
});

// Simple JS terminal
const terminalOutputElem = document.getElementById('terminalOutput');
document.getElementById('terminalInput').addEventListener('keydown', async (e) => {
  if (e.key === 'Enter') {
    const cmd = e.target.value.trim();
    if (!cmd) return;
    e.target.value = '';
    terminalOutputElem.textContent += '> ' + cmd + '\n';
    try {
      const result = await eval(`(async () => { return ${cmd} })()`);
      terminalOutputElem.textContent += String(result) + '\n';
    } catch (err) {
      terminalOutputElem.textContent += 'Error: ' + err + '\n';
    }
    terminalOutputElem.scrollTop = terminalOutputElem.scrollHeight;
  }
});
