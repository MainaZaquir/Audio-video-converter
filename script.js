function convertAudioToVideo() {
    const audioFile = document.getElementById('audioFile').files[0];
    const outputVideoFile = document.getElementById('outputVideoFile').value;

    const formData = new FormData();
    formData.append('audioFile', audioFile);
    formData.append('outputVideoFile', outputVideoFile);

    fetch('/convert/audio_to_video', {
        method: 'POST',
        body: formData
    })
    .then(response => response.text())
    .then(result => {
        document.getElementById('output').textContent = result;
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function convertVideoToAudio() {
    const videoFile = document.getElementById('videoFile').files[0];
    const outputAudioFile = document.getElementById('outputAudioFile').value;

    const formData = new FormData();
    formData.append('videoFile', videoFile);
    formData.append('outputAudioFile', outputAudioFile);

    fetch('/convert/video_to_audio', {
        method: 'POST',
        body: formData
    })
    .then(response => response.text())
    .then(result => {
        document.getElementById('output').textContent = result;
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
