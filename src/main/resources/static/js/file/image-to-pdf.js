const submitBtn = document.querySelector('#submit-btn');
const resultContainer = document.querySelector('#complete-image');

submitBtn.onclick = () => {
    if (imageData.length === 0) {
        alert('파일을 추가해주세요');
        return;
    }

    let data = new FormData();

    for (let image of imageData){
        data.append("images", image);
    }



    fetch('file/image-to-pdf', {
        method: 'POST',
        body: data
    })
        .then(resp => resp.blob())
        .then(value => {
            console.log(value);
            console.log(URL.createObjectURL(value));
            let url = URL.createObjectURL(value);
            resultContainer.insertAdjacentHTML('beforeend',
                `<div class="complete-content">
                    <img src="/image/icon-pdf.png" alt="PDF-ICON"/><br>
                    <a href="${url}" download="image_to_pdf.pdf">image_to_pdf.pdf</a>
                </div>`)
        })

}