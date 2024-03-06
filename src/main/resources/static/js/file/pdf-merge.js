const submitBtn = document.querySelector('#submit-btn');
const resultContainer = document.querySelector('#complete-container');


submitBtn.onclick = () => {
    if (fileData.length === 0) {
        alert('파일을 추가해주세요');
        return;
    }
    
    
    let data = new FormData();
    for (let file of fileData) {
        data.append("pdfs", file);
    }

    fetch('/file/pdf-merge', {
        method: 'POST',
        body: data
    }).then(resp => {
        console.log(resp.ok);
        return resp.blob();
    }).then(data => {
        console.log(data);
        let url = URL.createObjectURL(data);
        resultContainer.insertAdjacentHTML('beforeend',
            `<div class="complete-content">
                    <img src="/image/icon-pdf.png" alt="PDF-ICON"/><br>
                    <a href="${url}" download="merge_pdf.pdf">merge_pdf.pdf</a>
                </div>`)
    })
}