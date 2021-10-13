$(function () {
    $(".sidebar-link").click(function () {
     $(".sidebar-link").removeClass("is-active");
     $(this).addClass("is-active");
    });
   });

   $(window)
    .resize(function () {
     if ($(window).width() > 1090) {
      $(".sidebar").removeClass("collapse");
     } else {
      $(".sidebar").addClass("collapse");
     }
    })
    .resize();

   const allVideos = document.querySelectorAll(".video");
   const PostVideos = document.querySelectorAll(".random-video");
   const RandVideos = [
    'https://pixabay.com/ko/videos/download/video-66004_tiny.mp4?attachment',
    'https://pixabay.com/ko/videos/download/video-82602_small.mp4?attachment',
    'https://pixabay.com/ko/videos/download/video-67201_tiny.mp4?attachment'
   ];
   PostVideos.forEach((v) => {
      const Newrand = Math.floor(Math.random()*RandVideos.length);
      v.innerHTML = `<source src=${RandVideos[Newrand]} type="video/mp4">`;
   });


   allVideos.forEach((v) => {
    v.addEventListener("mouseover", () => {
     const video = v.querySelector("video");
     video.play();
    });
    v.addEventListener("mouseleave", () => {
     const video = v.querySelector("video");
     video.pause();
    });
   });

   $(function () {
    $(".logo, .logo-expand, .discover").on("click", function (e) {
     $(".main-container").removeClass("show");
     $(".main-container").scrollTop(0);
    });
    $(".recommend, .video").on("click", function (e) {
     $(".main-container").addClass("show");
     $(".main-container").scrollTop(0);
     $(".sidebar-link").removeClass("is-active");
     $(".recommend").addClass("is-active");
    });

    $(".video").click(function () {
     var source = $(this).find("source").attr("src");
     var title = $(this).find(".video-name").text();
     var person = $(this).find(".video-by").text();
     var img = $(this).find(".author-img").attr("src");
     $(".video-stream video").stop();
     $(".video-stream source").attr("src", source);
     $(".video-stream video").load();
     $(".video-p-title").text(title);
     $(".video-p-name").text(person);
     $(".video-detail .author-img").attr("src", img);
    });
   });
