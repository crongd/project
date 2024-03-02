const submitBtn = document.querySelector('#submit-btn');
const width = document.querySelector('#width');
const height = document.querySelector('#height');
submitBtn.onclick = () => {
    const files = fileInput.files;
    console.log(files)
    console.log(width.value)
    console.log(height.value)


    let data = new FormData();
    for (const file of files) {
        data.append('images', file);
    }
    data.append('width', width.value);
    data.append('height', height.value);

    fetch('/image/size', {
        method: 'POST',
        body: data
    }).then(resp => resp.json())
        .then(images => {
            const imgContainer = document.querySelector('#complete-img-container');
            images.forEach((image, index) => {
                imgContainer.insertAdjacentHTML('beforeend',
                    `<img class="complete-img" src="data:image/jpeg;base64,${image}" alt="처리된 이미지">`)
            })

        })
        .catch(e => console.log('패치 에러남'))
}