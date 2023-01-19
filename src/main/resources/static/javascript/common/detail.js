const detailImg = document.querySelectorAll(".paragraph-image");

detailImg.forEach(img => {
    const childDiv = img.firstChild;
    childDiv.style.width = "100%";
});