const submitBtn = document.querySelector('#submit-btn');
const rotationSelect = document.querySelector('#rotation-selection');

submitBtn.onclick = () => {
    const files = fileInput.files;
    console.log(files);
    console.log(rotationSelect.value);

    let data = new FormData();
    for (const file of files) {
        data.append('images', file);
    }

    data.append('rotation', rotationSelect.value);

    fetch('/image/rotation', {
        method: 'POST',
        body: data
    }).then(resp => resp.json())
        .then(data => {
            const imgContainer = document.querySelector('#complete-img-container');
            data.forEach(item => {
                console.log("name: " + item.name);
                console.log("image: " + item.image);
                imgContainer.insertAdjacentHTML('beforeend',
                    `<div>
                            <img class="complete-img" src="${item.image}" alt="처리된 이미지"><br>
                            <a href="${item.image}" download="${item.name}">${item.name}</a>
                        </div>`)
            })
        })
}