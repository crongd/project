const imageSection = document.querySelector('#image-section');
const imgLabel = imageSection.querySelector('#img-label');
const fileInput = imageSection.querySelector('#file-input');
const imgViewContainer = document.querySelector('#img-view-container');


fileInput.onchange = () => {
    const fileArr = fileInput.files;
    const reg = /(.*?)\.(jpg|jpeg|png|gif|bmp)$/;

    for (let file of fileArr) {
        if (file.name.match(reg)) {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = (data) => {
                let result = data.target.result;
                imgViewContainer.insertAdjacentHTML('beforeend',
                    `<div class="img-item">
                        <img src="${result}" alt="">
<!--                        <span></span>-->
                     </div>`)
            }
        } else {
            alert(file.name + "이 파일은 이미지가 아님");
            return;
        }
    }

}



// function image_file_check(files) {
//     const reg = /(.*?)\.(jpg|jpeg|png|gif|bmp)$/;
//     for (let file of files) {
//         if (!file.name.match(reg)) {
//             alert('파일 확장자명이 이미지가 아님');
//             return;
//         }
//     }
// }