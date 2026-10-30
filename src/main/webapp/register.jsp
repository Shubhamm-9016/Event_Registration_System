<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Register for Event | EventSpot</title><link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"></head>
<body><header class="navbar"><a class="brand" href="${pageContext.request.contextPath}/events"><span class="brand-mark">E</span> EventSpot</a><nav><a href="${pageContext.request.contextPath}/events">Explore Events</a><a class="nav-cta" href="${pageContext.request.contextPath}/registrations">View All Registrations</a></nav></header>
<main class="form-page"><a class="back-link" href="${pageContext.request.contextPath}/events">← Back to events</a><div class="form-layout">
  <section class="selected-event"><c:choose><c:when test="${not empty event}"><img src="${pageContext.request.contextPath}/${event.imageUrl}" alt="Event image" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/event-placeholder.svg';"><div class="selected-event-content"><span class="eyebrow">YOU'RE SIGNING UP FOR</span><h1><c:out value="${event.eventName}"/></h1><p><c:out value="${event.description}"/></p><div class="event-venue"><span class="meta-icon">▦</span><c:out value="${event.eventDate}"/></div><div class="event-venue"><span class="meta-icon">⌖</span><c:out value="${event.venue}"/></div></div></c:when><c:otherwise><div class="selected-event-content"><h1>Complete your registration</h1><p>Enter your details below to reserve your place.</p></div></c:otherwise></c:choose></section>
  <section class="form-card"><span class="eyebrow">QUICK & EASY</span><h2>Participant details</h2><p class="form-intro">Fill in your details to complete your registration.</p>
    <c:if test="${not empty error}"><div class="alert alert-error"><c:out value="${error}"/></div></c:if>
    <form action="${pageContext.request.contextPath}/register" method="post">
      <input type="hidden" name="eventId" value="<c:out value='${not empty event ? event.eventId : eventId}'/>">
      <label for="name">Full name</label><input id="name" name="name" type="text" maxlength="100" autocomplete="name" placeholder="e.g. Shubham Gupta" value="<c:out value='${name}'/>" required>
      <label for="email">Email address</label><input id="email" name="email" type="email" maxlength="150" autocomplete="email" placeholder="you@example.com" value="<c:out value='${email}'/>" required>
      <label for="phone">Phone number</label><input id="phone" name="phone" type="tel" maxlength="15" pattern="[0-9+() -]{7,15}" autocomplete="tel" placeholder="Your contact number" value="<c:out value='${phone}'/>" required>
      <button class="button button-primary submit-button" type="submit">Confirm Registration <span aria-hidden="true">→</span></button>
    </form><p class="privacy-note">Please check your details before submitting.</p>
  </section>
</div></main><footer class="footer"><span>EventSpot</span><span>Discover something worth showing up for.</span></footer></body></html>
