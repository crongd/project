const imageSection = document.querySelector('#image-section');
const imgLabel = document.querySelector('#img-label');
const fileInput = document.querySelector('#file-input');
const imgViewContainer = document.querySelector('#img-view-container');

// console.log(fileInput)
fileInput.onchange = () => {
    const fileArr = fileInput.files;
    const reg = /(.*?)\.(jpg|jpeg|png|gif|bmp)$/;
    console.log(fileInput.files);
    imgViewContainer.innerHTML = '';
    for (let file of fileArr) {
        if (file.name.match(reg)) {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = (data) => {
                let img = data.target.result;
                let name = file.name;
                imgViewContainer.insertAdjacentHTML('beforeend',
                    `<div class="img-item">
                    <img src="${img}" alt="사진">
                    <span>${name}</span>
                 </div>`)
            }
        } else {
            alert(file.name + "이 파일은 이미지가 아님");
            return;
        }
    }
}

imgLabel.ondragenter = (event) => {
    event.preventDefault();
    console.log('안으로 들어옴')
}

imgLabel.ondragleave = (event) => {
    event.preventDefault();
    console.log('밖으로 나감');
}

imgLabel.ondrop = (event) => {
    event.preventDefault();
    console.dir('안에 드랍함');
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


