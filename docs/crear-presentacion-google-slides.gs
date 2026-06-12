function crearPresentacionFitReserve() {
  const deck = SlidesApp.create('FitReserve API - Presentacion final');
  const slides = deck.getSlides();
  slides[0].remove();

  const data = [
    {
      title: 'FitReserve API',
      subtitle: 'Backend de reservas de clases de gimnasio\nJuan Garcia',
      bullets: []
    },
    {
      title: 'Sobre mi',
      subtitle: '',
      bullets: [
        'Me interesa el desarrollo backend.',
        'Queria practicar una API REST real con seguridad.',
        'Elegi un gimnasio porque tiene usuarios, clases, salas y reservas.'
      ]
    },
    {
      title: 'Reservar clases necesita orden y control',
      subtitle: '',
      bullets: [
        'Los socios necesitan ver clases disponibles.',
        'El gimnasio necesita controlar salas y capacidad.',
        'Los administradores necesitan gestionar clases y reservas.',
        'El sistema debe evitar reservas duplicadas o clases llenas.'
      ]
    },
    {
      title: 'FitReserve conecta usuarios, salas, clases y reservas',
      subtitle: '',
      bullets: [
        'Registro e inicio de sesion.',
        'Roles: ADMIN, TRAINER y MEMBER.',
        'CRUD de salas y clases.',
        'Reservas y cancelaciones.',
        'Pantalla web local para probar la API.'
      ]
    },
    {
      title: 'El modelo usa relaciones JPA y herencia JOINED',
      subtitle: '',
      bullets: [
        'Usuario realiza reservas.',
        'Sala contiene clases.',
        'ClaseGimnasio es la clase padre abstracta.',
        'ClaseCardio, ClaseFuerza y ClaseMenteCuerpo heredan de ClaseGimnasio.',
        'Reserva une Usuario con ClaseGimnasio.'
      ]
    },
    {
      title: 'Spring Boot organiza la aplicacion por capas',
      subtitle: '',
      bullets: [
        'Controllers: reciben peticiones HTTP.',
        'DTOs: validan y transportan datos.',
        'Services: contienen la logica de negocio.',
        'Repositories: acceden a MySQL con Spring Data JPA.',
        'Security: gestiona JWT y roles.'
      ]
    },
    {
      title: 'Reto tecnico',
      subtitle: 'Combinar JWT, roles y relaciones JPA',
      bullets: [
        'Proteger rutas segun el rol del usuario.',
        'Evitar errores de serializacion con relaciones lazy.',
        'Devolver DTOs en lugar de entidades completas.',
        'Mantener reglas de negocio en los servicios.'
      ]
    },
    {
      title: 'Error y aprendizaje',
      subtitle: 'Separar configuracion, seguridad y salida de datos',
      bullets: [
        'No subir contrasenas reales a GitHub.',
        'Documentar TU_PASSWORD para cada entorno.',
        'Usar DTOs para respuestas limpias.',
        'Probar rutas desde navegador y ejemplos HTTP.'
      ]
    },
    {
      title: 'DEMO',
      subtitle: 'Aplicacion local: http://localhost:8080/\nGitHub: https://github.com/juangarcia15525/FitReserve\n\nGracias',
      bullets: []
    }
  ];

  data.forEach((item, index) => {
    const slide = deck.appendSlide(SlidesApp.PredefinedLayout.BLANK);
    slide.getBackground().setSolidFill(index === 0 || index === data.length - 1 ? '#0F172A' : '#F8FAFC');

    const title = slide.insertTextBox(item.title, 45, 45, 620, 85);
    title.getText().getTextStyle()
      .setFontFamily('Arial')
      .setFontSize(index === 0 ? 42 : 30)
      .setBold(true)
      .setForegroundColor(index === 0 || index === data.length - 1 ? '#FFFFFF' : '#0F172A');

    if (item.subtitle) {
      const subtitle = slide.insertTextBox(item.subtitle, 50, index === 0 ? 160 : 120, 610, 130);
      subtitle.getText().getTextStyle()
        .setFontFamily('Arial')
        .setFontSize(index === 0 || index === data.length - 1 ? 22 : 18)
        .setForegroundColor(index === 0 || index === data.length - 1 ? '#DBEAFE' : '#334155');
    }

    if (item.bullets.length > 0) {
      const body = slide.insertTextBox(item.bullets.map(b => '• ' + b).join('\n'), 70, 155, 610, 260);
      body.getText().getTextStyle()
        .setFontFamily('Arial')
        .setFontSize(18)
        .setForegroundColor('#1E293B');
    }

    const footer = slide.insertTextBox('FitReserve API · Proyecto final', 45, 500, 400, 24);
    footer.getText().getTextStyle()
      .setFontFamily('Arial')
      .setFontSize(10)
      .setForegroundColor(index === 0 || index === data.length - 1 ? '#CBD5E1' : '#64748B');
  });

  Logger.log('Presentacion creada: ' + deck.getUrl());
}
