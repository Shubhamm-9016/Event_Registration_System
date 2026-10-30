<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>EventSpot | Discover Events</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header class="navbar">
  <a class="brand" href="${pageContext.request.contextPath}/events"><span class="brand-mark">E</span> EventSpot</a>
  <nav><a href="${pageContext.request.contextPath}/events">Explore Events</a><a class="nav-cta" href="${pageContext.request.contextPath}/registrations">View All Registrations</a></nav>
</header>
<section class="hero">
  <div class="hero-content"><span class="eyebrow">MAKE YOUR NEXT MOMENT COUNT</span>
    <h1>Discover events.<br><span>Make connections.</span></h1>
    <p>Find workshops, talks and campus celebrations. Your next great experience starts here.</p>
    <a class="button button-primary hero-button" href="#events">Explore events <span aria-hidden="true">↓</span></a>
  </div>
  <div class="hero-decoration"><div class="decor-circle circle-one"></div><div class="decor-circle circle-two"></div><div class="hero-note"><span class="note-icon">✦</span><div><strong>Something exciting</strong><small>is waiting for you</small></div></div></div>
</section>
<main id="events" class="main-content">
  <div class="section-heading"><div><span class="eyebrow">WHAT'S HAPPENING</span><h2>Explore upcoming events</h2><p>Pick an event and reserve your place in just a few steps.</p></div><span class="event-count"><c:out value="${events.size()}"/> events</span></div>
  <c:choose><c:when test="${empty events}"><div class="empty-state"><div class="empty-icon">✦</div><h3>No events available yet</h3><p>Please check back soon for upcoming events.</p></div></c:when><c:otherwise>
  <div class="events-grid">
    <c:forEach var="event" items="${events}">
      <article class="event-card">
        <div class="event-image-wrap"><img class="event-image" src="${pageContext.request.contextPath}/${event.imageUrl}" alt="Event image" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/event-placeholder.svg';"><span class="image-label">UPCOMING EVENT</span></div>
        <div class="event-details"><div class="event-meta"><span class="meta-icon">▦</span><c:out value="${event.eventDate}"/></div>
          <h3><c:out value="${event.eventName}"/></h3><p class="event-description"><c:out value="${event.description}"/></p>
          <div class="event-venue"><span class="meta-icon">⌖</span><span><c:out value="${event.venue}"/></span></div>
          <a class="button button-primary register-btn" href="${pageContext.request.contextPath}/register?eventId=${event.eventId}">Register Now <span aria-hidden="true">→</span></a>
        </div>
      </article>
    </c:forEach>
  </div></c:otherwise></c:choose>
  <div class="registrations-callout"><div><span class="eyebrow">ALREADY REGISTERED?</span><h3>Keep track of every sign-up.</h3><p>View the registration list and event details in one place.</p></div><a class="button button-outline" href="${pageContext.request.contextPath}/registrations">View registrations <span aria-hidden="true">→</span></a></div>
</main>
<footer class="footer"><span>EventSpot</span><span>Discover something worth showing up for.</span></footer>
</body></html>
