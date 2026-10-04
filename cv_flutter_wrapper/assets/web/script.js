/**
 * BioApp Web CV - Interacciones JavaScript
 * Daniela Madelain Erazo Montenegro - Universidad Politécnica Estatal del Carchi (UPEC)
 */

document.addEventListener('DOMContentLoaded', () => {
  const savedTheme = localStorage.getItem('theme') || (window.matchMedia('(prefers-color-scheme: light)').matches ? 'light' : 'dark');
  window.setTheme(savedTheme);

  const savedPhoto = localStorage.getItem('daniela_profile_photo');
  if (savedPhoto) {
    const profileImg = document.getElementById('profileImage');
    if (profileImg) profileImg.src = savedPhoto;
  }

  initScrollSpy();
});

function uploadProfilePhoto(event) {
  const file = event.target.files[0];
  if (!file) return;

  const reader = new FileReader();
  reader.onload = function(e) {
    const photoDataUrl = e.target.result;
    const profileImg = document.getElementById('profileImage');
    if (profileImg) {
      profileImg.src = photoDataUrl;
    }
    try {
      localStorage.setItem('daniela_profile_photo', photoDataUrl);
      console.log('[BioApp Web CV] Foto de perfil actualizada correctamente.');
    } catch (err) {
      console.warn('No se pudo guardar la imagen en localStorage:', err);
    }
  };
  reader.readAsDataURL(file);
}

window.setTheme = function(theme) {
  const normalizedTheme = (theme === 'light' || theme === 'dark') ? theme : 'dark';
  document.documentElement.setAttribute('data-theme', normalizedTheme);
  localStorage.setItem('theme', normalizedTheme);

  const themeIconWrapper = document.getElementById('themeIcon');
  if (themeIconWrapper) {
    if (normalizedTheme === 'light') {
      themeIconWrapper.innerHTML = `<svg class="svg-icon" viewBox="0 0 24 24"><path d="M12.3 2a10 10 0 0 0 9.7 11.5 10 10 0 1 1-9.7-11.5z"/></svg>`;
    } else {
      themeIconWrapper.innerHTML = `<svg class="svg-icon" viewBox="0 0 24 24"><path d="M12 7c-2.76 0-5 2.24-5 5s2.24 5 5 5 5-2.24 5-5-2.24-5-5-5zM2 13h2c.55 0 1-.45 1-1s-.45-1-1-1H2c-.55 0-1 .45-1 1s.45 1 1 1zm18 0h2c.55 0 1-.45 1-1s-.45-1-1-1h-2c-.55 0-1 .45-1 1s.45 1 1 1zM11 2v2c0 .55.45 1 1 1s1-.45 1-1V2c0-.55-.45-1-1-1s-1 .45-1 1zm0 18v2c0 .55.45 1 1 1s1-.45 1-1v-2c0-.55-.45-1-1-1s-1 .45-1 1zM5.99 4.58c-.39-.39-1.03-.39-1.41 0s-.39 1.03 0 1.41l1.06 1.06c.39.39 1.03.39 1.41 0s.39-1.03 0-1.41L5.99 4.58zm12.37 12.37c-.39-.39-1.03-.39-1.41 0s-.39 1.03 0 1.41l1.06 1.06c.39.39 1.03.39 1.41 0s.39-1.03 0-1.41l-1.06-1.06zm1.06-10.96c.39-.39.39-1.03 0-1.41s-1.03-.39-1.41 0l-1.06 1.06c-.39.39-.39 1.03 0 1.41s1.03.39 1.41 0l1.06-1.06zM7.05 18.36c.39-.39.39-1.03 0-1.41s-1.03-.39-1.41 0l-1.06 1.06c-.39.39-.39 1.03 0 1.41s1.03.39 1.41 0l1.06-1.06z"/></svg>`;
    }
  }

  console.log(`[BioApp Web CV] Tema cambiado a: ${normalizedTheme}`);
};

function toggleTheme() {
  const currentTheme = document.documentElement.getAttribute('data-theme') || 'dark';
  const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
  window.setTheme(newTheme);
}

function toggleAccordion(headerElem) {
  const itemElem = headerElem.closest('.accordion-item');
  if (!itemElem) return;

  const isActive = itemElem.classList.contains('active');
  const allItems = document.querySelectorAll('.accordion-item');

  allItems.forEach(item => {
    item.classList.remove('active');
    const header = item.querySelector('.accordion-header');
    if (header) header.setAttribute('aria-expanded', 'false');
  });

  if (!isActive) {
    itemElem.classList.add('active');
    headerElem.setAttribute('aria-expanded', 'true');
  }
}

function filterSkills(category, buttonElem) {
  const allChips = document.querySelectorAll('.chip-filter');
  allChips.forEach(chip => chip.classList.remove('active'));
  if (buttonElem) buttonElem.classList.add('active');

  const skillCards = document.querySelectorAll('.skill-card');
  skillCards.forEach(card => {
    const cardCategory = card.getAttribute('data-category');
    if (category === 'Todas' || cardCategory === category) {
      card.style.display = 'block';
      card.style.animation = 'fadeIn 0.3s ease-in-out';
    } else {
      card.style.display = 'none';
    }
  });
}

function handleContactSubmit(event) {
  event.preventDefault();

  const nameInput = document.getElementById('contactName');
  const emailInput = document.getElementById('contactEmail');
  const messageInput = document.getElementById('contactMessage');
  const successAlert = document.getElementById('formSuccessAlert');

  let isValid = true;

  [nameInput, emailInput, messageInput].forEach(input => input.classList.remove('is-invalid'));
  if (successAlert) successAlert.style.display = 'none';

  if (!nameInput.value.trim()) {
    nameInput.classList.add('is-invalid');
    isValid = false;
  }

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailInput.value.trim() || !emailRegex.test(emailInput.value.trim())) {
    emailInput.classList.add('is-invalid');
    isValid = false;
  }

  if (!messageInput.value.trim()) {
    messageInput.classList.add('is-invalid');
    isValid = false;
  }

  if (isValid) {
    const nombre = nameInput.value.trim();
    const email = emailInput.value.trim();
    const mensaje = messageInput.value.trim();

    const whatsappText = `¡Hola Daniela Erazo! Te escribo desde tu Hoja de Vida Web (UPEC):\n\n` +
      `👤 *Nombre:* ${nombre}\n` +
      `✉️ *Correo:* ${email}\n` +
      `💬 *Mensaje:* ${mensaje}`;

    const whatsappUrl = `https://wa.me/593939484810?text=${encodeURIComponent(whatsappText)}`;

    if (successAlert) successAlert.style.display = 'flex';
    document.getElementById('contactForm').reset();
    window.open(whatsappUrl, '_blank');
  }
}

function exportVCard() {
  alert("📇 vCard Exportada: Daniela Madelain Erazo Montenegro\n📱 Celular: 0939484810\n✉️ Email: erazodaniela1995@gmail.com\n🎓 UPEC - Ingeniería en Computación");
}

function initScrollSpy() {
  const sections = document.querySelectorAll('section[id]');
  const navLinks = document.querySelectorAll('.nav-link');

  window.addEventListener('scroll', () => {
    let currentSection = '';
    sections.forEach(section => {
      const sectionTop = section.offsetTop - 120;
      if (window.scrollY >= sectionTop) {
        currentSection = section.getAttribute('id');
      }
    });

    navLinks.forEach(link => {
      link.classList.remove('active');
      if (link.getAttribute('href') === `#${currentSection}`) {
        link.classList.add('active');
      }
    });
  });
}
