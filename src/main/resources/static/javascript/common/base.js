async function fetchAsText(url) {
    return await (await fetch(url)).text();
}

async function importPage(targetDiv, page) {
    document.querySelector('#' + targetDiv).innerHTML = await fetchAsText(page);
}

importPage('header', "/page/header.html");
importPage('nav', "/page/nav.html");
importPage('footer', "/page/footer.html");