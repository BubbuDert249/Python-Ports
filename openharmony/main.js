function switchTab(tab) {
    document.getElementById('script-tab').classList.toggle('active', tab === 'script');
    document.getElementById('terminal-tab').classList.toggle('active', tab === 'terminal');
}
