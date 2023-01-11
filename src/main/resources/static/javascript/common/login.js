const urlParams = new URLSearchParams(window.location.search);
const invalidID = document.getElementById("invalidID");
const invalidPW = document.getElementById("invalidPW");


// URLSearchParams.has()
if (urlParams.has('exception')) {
    if (urlParams.get('exception') === 'invalidID') {
        invalidID.style.display = 'block';
    }

    if (urlParams.get('exception') === 'invalidPW') {
        invalidPW.style.display = 'block';
    }
}