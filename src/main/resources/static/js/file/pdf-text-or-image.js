const submitBtn = document.querySelector('#submit-btn');
const startPage = document.querySelector('#startNo');
const endPage = document.querySelector('#endNo');
const resultText = document.querySelector('#complete-text');
const resultImage = document.querySelector('#complete-image');


submitBtn.onclick = () => {
    console.log('클릭')
    console.log(fileData)
    if (fileData.length > 1) {
        alert('텍스트 및 이미지 추출은 1개의 파일만 가능합니다.');
        imgViewContainer.innerHTML = '';
        return;
    }
    resultText.innerText = '';
    resultImage.innerHTML = '';

    let data = new FormData();
    data.append("pdf", fileData[0]);
    data.append("startPage", startPage.value);
    data.append("endPage", endPage.value);


    fetch('/file/pdf-text-or-image', {
        method: 'POST',
        body: data
    }).then(resp => resp.json())
        .then(result => {
            if (result.text != null) {
                resultText.innerText = result.text;
            }

            if (result.image != null) {
                for (let image of result.image) {
                    resultImage.insertAdjacentHTML('beforeend',
                        `<div>
                                <img class="complete-img" src="data:image/jpeg;base64,${image}" alt="처리된 이미지"><br>
                                <a href="data:image/jpeg;base64,${image}" download="다운로드.jpeg">다운로드</a>
                            </div>`)
                }
            }
        }).catch(e => {
        console.log('에러남' + e)
    })
}