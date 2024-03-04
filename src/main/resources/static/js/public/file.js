// const imageSection = document.querySelector('#image-section');
const imgLabel = document.querySelector('#img-label');
const fileInput = document.querySelector('#file-input');
const imgViewContainer = document.querySelector('#img-view-container');
let fileData = [];
// console.log(imgLabel);

// console.log(fileInput)
fileInput.onchange = () => {
    console.log('채인지 됨')
    // const fileArr = fileInput.files;
    imageControl(fileInput.files);

}

function imageControl(files) {
    const reg = /(.*?)\.(pdf)$/;

    imgViewContainer.innerHTML = '';
    for (let file of files) {
        if (file.name.match(reg)) {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = (data) => {
                let img = data.target.result;
                let name = file.name;
                imgViewContainer.insertAdjacentHTML('beforeend',
                    `<div class="img-item">
                    <img src="/image/icon-pdf.png" />
                    <span>${name}</span>
                 </div>`)
            }
            fileData.push(file);
        } else {
            alert(file.name + " \n이 파일은 이미지가 아님");
            return;
        }
    }
    console.log(fileData);
}


imgLabel.ondragenter = event => {
    // event.preventDefault();
    console.log('드래그 시작..');
    imgLabel.style.backgroundColor = 'blue';
}
imgLabel.ondragleave = event => {
    // event.preventDefault();
    console.log('드래그 나감..');
    imgLabel.style.backgroundColor = 'white';
}
imgLabel.ondragover = event => {
    event.preventDefault();
}
imgLabel.ondragend = event => {
    // event.preventDefault()
}
imgLabel.ondrop = event => {
    event.preventDefault();
    imgLabel.style.backgroundColor = 'white';
    imageControl(event.dataTransfer.files);
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


