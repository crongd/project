const submitBtn = document.querySelector('#submit-btn');
const resultContainer = document.querySelector('#complete-image');
const startPage = document.querySelector('#startNo');
const endPage = document.querySelector('#endNo');

submitBtn.onclick = () => {
    if (fileData.length === 0) {
        alert('파일을 추가해주세요');
        return;
    }

    let data = new FormData();
    for (let file of fileData) {
        data.append("pdfs", file);
    }

    console.log(startPage.value);
    console.log(endPage.value)

    if (startPage.value !== '') {
        data.append("startPage", startPage.value);
    }

    if (endPage.value !== '') {
        data.append("endPage", endPage.value);
    }

    console.log(data);

    fetch('/file/pdf-to-jpg', {
        method: 'POST',
        body: data
    }).then(resp => resp.json())
        .then(images => {
            for (let image of images) {
                resultContainer.insertAdjacentHTML('beforeend',
                    `<div>
                             <img class="complete-img" src="data:image/jpeg;base64,${image}" alt="처리된 이미지"><br>
                             <a href="data:image/jpeg;base64,${image}" download="다운로드.jpeg">다운로드</a>
                        </div>`);
            }
        })

}