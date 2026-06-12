function crearPresentacionFitReserve() {
  const deck = SlidesApp.create('FitReserve API - Presentacion final');
  const first = deck.getSlides()[0];
  first.remove();

  const colors = {
    dark: '#0F172A',
    blue: '#2563EB',
    green: '#16A34A',
    orange: '#F97316',
    purple: '#7C3AED',
    bg: '#F8FAFC',
    text: '#0F172A',
    muted: '#475569',
    white: '#FFFFFF'
  };

  const slides = [
    {
      title: 'FitReserve API',
      subtitle: 'Backend de reservas de clases de gimnasio\nSpring Boot · MySQL · JPA · JWT\n\nJuan Garcia',
      bullets: [],
      dark: true
    },
    {
      title: 'Sobre mi',
      subtitle: '',
      bullets: [
        'Me interesa el desarrollo backend.',
        'Queria practicar una API REST real.',
        'Elegi un gimnasio porque tiene usuarios, clases, salas, reservas y seguridad.'
      ]
    },
    {
      title: 'Reservar clases necesita orden y control',
      subtitle: '',
      bullets: [
        'Los socios necesitan consultar clases disponibles.',
        'El gimnasio necesita controlar salas y capacidad.',
        'Los administradores necesitan gestionar clases y reservas.',
        'El sistema evita reservas duplicadas y clases llenas.'
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
      title: 'Diagrama UML actualizado',
      subtitle: 'Modelo principal del proyecto actual',
      diagram: true
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
      title: 'JWT protege las rutas segun el tipo de usuario',
      subtitle: '',
      bullets: [
        'Las rutas publicas permiten registro, login y consulta.',
        'ADMIN puede crear, editar y borrar salas y clases.',
        'MEMBER puede reservar y cancelar sus reservas.',
        'El token Bearer identifica al usuario en cada peticion.'
      ]
    },
    {
      title: 'Reto tecnico',
      subtitle: 'Combinar JPA, DTOs y seguridad',
      bullets: [
        'Evitar errores al devolver entidades con relaciones lazy.',
        'Proteger correctamente rutas por rol.',
        'Devolver respuestas limpias con DTOs.',
        'Mantener las reglas de negocio en los servicios.'
      ]
    },
    {
      title: 'Error y aprendizaje',
      subtitle: 'Preparar un proyecto para entrega real',
      bullets: [
        'No subir contrasenas reales a GitHub.',
        'Dejar TU_PASSWORD para que cada persona ponga su contrasena.',
        'Probar rutas desde navegador y ejemplos HTTP.',
        'Documentar diagramas, rutas y configuracion.'
      ]
    },
    {
      title: 'DEMO',
      subtitle: 'Aplicacion local: http://localhost:8080/\nGitHub: https://github.com/juangarcia15525/FitReserve\nDiagrama UML: docs/diagrama-uml-nuevo.md\n\nGracias',
      bullets: [],
      dark: true
    }
  ];

  slides.forEach((data, index) => {
    const slide = deck.appendSlide(SlidesApp.PredefinedLayout.BLANK);
    slide.getBackground().setSolidFill(data.dark ? colors.dark : colors.bg);

    addTitle(slide, data.title, data.dark, index + 1);

    if (data.subtitle) {
      addSubtitle(slide, data.subtitle, data.dark, data.diagram ? 92 : 132);
    }

    if (data.diagram) {
      addDiagram(slide, colors);
    } else if (data.bullets && data.bullets.length > 0) {
      addBullets(slide, data.bullets);
    }

    addFooter(slide, data.dark, index + 1, slides.length);
  });

  Logger.log('Presentacion creada: ' + deck.getUrl());
}

function addTitle(slide, text, dark, page) {
  const box = slide.insertTextBox(text, 42, 34, 650, 70);
  box.getText().getTextStyle()
    .setFontFamily('Arial')
    .setFontSize(page === 1 ? 39 : 29)
    .setBold(true)
    .setForegroundColor(dark ? '#FFFFFF' : '#0F172A');
}

function addSubtitle(slide, text, dark, y) {
  const box = slide.insertTextBox(text, 48, y, 620, 170);
  box.getText().getTextStyle()
    .setFontFamily('Arial')
    .setFontSize(dark ? 22 : 18)
    .setForegroundColor(dark ? '#DBEAFE' : '#334155');
}

function addBullets(slide, bullets) {
  const text = bullets.map(b => '• ' + b).join('\n');
  const box = slide.insertTextBox(text, 72, 150, 590, 285);
  box.getText().getTextStyle()
    .setFontFamily('Arial')
    .setFontSize(18)
    .setForegroundColor('#1E293B');
}

function addFooter(slide, dark, page, total) {
  const footer = slide.insertTextBox('FitReserve API · Proyecto final · ' + page + '/' + total, 42, 505, 450, 22);
  footer.getText().getTextStyle()
    .setFontFamily('Arial')
    .setFontSize(10)
    .setForegroundColor(dark ? '#CBD5E1' : '#64748B');
}

function addEntity(slide, label, x, y, w, h, fill, border) {
  const shape = slide.insertShape(SlidesApp.ShapeType.ROUND_RECTANGLE, x, y, w, h);
  shape.getFill().setSolidFill(fill);
  shape.getLine().setSolidFill(border).setWeight(1.5);
  shape.getText().setText(label);
  shape.getText().getTextStyle()
    .setFontFamily('Arial')
    .setFontSize(10)
    .setBold(true)
    .setForegroundColor('#0F172A');
  shape.getText().getParagraphStyle().setParagraphAlignment(SlidesApp.ParagraphAlignment.CENTER);
  return shape;
}

function addConnector(slide, x1, y1, x2, y2, label) {
  const line = slide.insertLine(SlidesApp.LineCategory.STRAIGHT, x1, y1, x2, y2);
  line.getLineFill().setSolidFill('#334155');
  line.setWeight(1.2);
  if (label) {
    const box = slide.insertTextBox(label, (x1 + x2) / 2 - 42, (y1 + y2) / 2 - 14, 95, 20);
    box.getText().getTextStyle().setFontFamily('Arial').setFontSize(8).setForegroundColor('#475569');
  }
}

function addDiagram(slide, colors) {
  const usuario = addEntity(slide, 'Usuario\n- id\n- nombre\n- email\n- password\n- rol', 35, 145, 122, 115, '#DCFCE7', colors.green);
  const reserva = addEntity(slide, 'Reserva\n- id\n- fechaReserva\n- estado', 235, 145, 126, 95, '#DBEAFE', colors.blue);
  const sala = addEntity(slide, 'Sala\n- id\n- nombre\n- capacidadMaxima\n- ubicacion', 555, 145, 125, 105, '#FFEDD5', colors.orange);
  const clase = addEntity(slide, 'ClaseGimnasio\n<<abstract>>\n- id\n- nombre\n- fechaInicio\n- fechaFin\n- capacidad\n- nivelIntensidad', 270, 285, 180, 140, '#F3E8FF', colors.purple);

  const cardio = addEntity(slide, 'ClaseCardio\n- caloriasEstimadas\n- tipoCardio', 85, 445, 125, 68, '#FAF5FF', colors.purple);
  const fuerza = addEntity(slide, 'ClaseFuerza\n- grupoMuscular\n- necesitaEquipamiento', 285, 445, 135, 68, '#FAF5FF', colors.purple);
  const mente = addEntity(slide, 'ClaseMenteCuerpo\n- nivelRelajacion\n- requiereEsterilla', 500, 445, 145, 68, '#FAF5FF', colors.purple);

  addConnector(slide, 157, 195, 235, 195, '1 a N reservas');
  addConnector(slide, 330, 240, 330, 285, 'N reservas');
  addConnector(slide, 450, 350, 555, 200, 'sala 1 a N');
  addConnector(slide, 95, 260, 270, 350, 'entrenador');
  addConnector(slide, 330, 425, 147, 445, 'hereda');
  addConnector(slide, 360, 425, 352, 445, 'hereda');
  addConnector(slide, 400, 425, 570, 445, 'hereda');

  const enums = slide.insertTextBox('Enums\nRol: ADMIN, TRAINER, MEMBER\nEstadoReserva: ACTIVA, CANCELADA, COMPLETADA\nNivelIntensidad: BAJO, MEDIO, ALTO', 475, 280, 215, 95);
  enums.getText().getTextStyle().setFontFamily('Arial').setFontSize(10).setForegroundColor('#0F172A');
}
