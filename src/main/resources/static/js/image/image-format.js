const submitBtn = document.querySelector('#submit-btn');
const formatSelect = document.querySelector('#format-selection');

submitBtn.onclick = () => {

    console.log(formatSelect.value);

    let data = new FormData();
    for (const image of imageData) {
        data.append('images', image);
    }

    data.append('format', formatSelect.value);

    fetch('/image/format', {
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
        }).catch(e => {
        console.log('에러남')
    })
}