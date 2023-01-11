function check_pw() {
    if (document.getElementById('pw').value !== '' && document.getElementById('pw2').value !== '') {
        if (document.getElementById('pw').value === document.getElementById('pw2').value) {
            document.getElementById('pw-alert').innerHTML = '비밀번호가 일치합니다.'
            document.getElementById('pw-alert').style.color = "var(--color-secondary)";
            document.getElementById('submit').disabled = false;
        }
        else {
            document.getElementById('pw-alert').innerHTML = '비밀번호가 일치하지 않습니다.';
            document.getElementById('pw-alert').style.color = "var(--color-red)";
            document.getElementById('submit').disabled = true;
            return;
        }
    }

}