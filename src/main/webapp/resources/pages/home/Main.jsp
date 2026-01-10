<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>About Us</title>
    <link rel="stylesheet" type="text/css" href="Main.css">
    <link href="https://fonts.googleapis.com/css2?family=Anton&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/footer/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/navbar/navbar.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<jsp:include page="/resources/common/navbar/navbar.jsp" />

<div class="hero container">
    <div class="hero_text">
        <h1>Discover Your Next Great Adventure</h1>
        <p>
            Welcome to Novel Nest, where every page turns into a new journey.
            We curate the world's best stories to bring imagination, knowledge,
            and inspiration directly to your doorstep.
        </p>
    </div>
</div>

<section class="vision_section">
    <div class="vision_container">
        <div class="vision_content">
            <h2 class="vision_header">Vision</h2>
            <p class="vision_text">
                To cultivate a global community of readers where knowledge is accessible,
                imagination is boundless, and every story finds its perfect home in the
                hands of someone who needs it.
            </p>
        </div>
        <div class="vision_image">
            <img src="${pageContext.request.contextPath}/images/vision.png" alt="Vision Image"/>
        </div>
    </div>
</section>

<section class="mission_section">
    <div class="mission_container">
        <div class="mission_image">
            <img src="${pageContext.request.contextPath}/images/mission.png" alt="Mission Image"/>
        </div>
        <div class="mission_content">
            <h2 class="mission_header">Mission</h2>
            <p class="mission_text">
                To empower readers of all ages by providing a diverse collection of
                literature that inspires, educates, and entertains, all while delivering
                a seamless and delightful book-buying experience.
            </p>
        </div>
    </div>
</section>

<section class="about_section">
    <div class="about_container">
        <div class="about_content">
            <h2 class="about_header">About Us</h2>
            <p class="about_text">
                Novel Nest isn't just a bookstore; it is a sanctuary for bibliophiles.
                We believe in the transformative power of reading. Like a well-written
                novel, our service is crafted with care to ensure you find the books
                that matter most to you.
            </p>
        </div>
        <div class="about_image">
            <img src="${pageContext.request.contextPath}/images/aboutus.png" alt="About Us Image"/>
        </div>
    </div>
</section>

<section class="story_section">
    <div class="story_container">
        <div class="story_image">
            <img src="${pageContext.request.contextPath}/images/story.png" alt="Story Image"/>
        </div>
        <div class="story_content">
            <h2 class="story_header">Our Story</h2>
            <p class="story_text">
                In 2024, fellow USM graduate students Tan Wei Neng and YUSUF MAK BIN MOHAMMAD OMAR
                had a vision: that finding the right book shouldn't be a chore—it should be a joy.
                They wanted to create a space where stories were cherished, not just sold.
                This dream became reality in 2025 when they founded Novel Nest.
            </p>
        </div>
    </div>
</section>

<jsp:include page="/resources/common/footer/footer.jsp" />

</body>
</html>