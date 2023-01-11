//대표 사진 올리기
const defaultBtn = document.querySelector("#selectImg");
const main_img = document.querySelector(".form__img-container img");
const explain = document.querySelector(".upload-icon");

function defaultBtnActive() {
    defaultBtn.click();
}

defaultBtn.addEventListener("change", function () {
    const file = this.files[0];

    if (file) {
        const reader = new FileReader();
        reader.onload = function () {
            const result = reader.result;
            main_img.src = result;
            main_img.classList.add("active");
            explain.classList.remove("active");
        }
        reader.readAsDataURL(file);
    }
})