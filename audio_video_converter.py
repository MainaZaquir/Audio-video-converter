import moviepy.editor as mp

def convert_audio_to_video(audio_file, output_file):
    audio = mp.AudioFileClip(audio_file)
    audio.write_videofile(output_file, audio=True)

def convert_video_to_audio(video_file, output_file):
    video = mp.VideoFileClip(video_file)
    video.audio.write_audiofile(output_file)

def main():
    print("Welcome to Audio-Video Converter!")
    print("1. Convert audio to video")
    print("2. Convert video to audio")

    choice = input("Enter your choice (1 or 2): ")

    if choice == '1':
        audio_file = input("Enter the audio file path: ")
        output_file = input("Enter the output video file path: ")
        convert_audio_to_video(audio_file, output_file)
    elif choice == '2':
        video_file = input("Enter the video file path: ")
        output_file = input("Enter the output audio file path: ")
        convert_video_to_audio(video_file, output_file)
    else:
        print("Invalid choice!")

if __name__ == '__main__':
    main()
  
