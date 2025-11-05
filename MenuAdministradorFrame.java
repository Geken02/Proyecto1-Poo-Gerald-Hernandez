package poo.proyecto1.admin;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import poo.proyecto1.curso.Curso;
import poo.proyecto1.persona.Persona;
import poo.proyecto1.persona.estudiante.Estudiante;
import poo.proyecto1.persona.personalogin.LoginFrame;
import poo.proyecto1.persona.profesor.Profesor;
import poo.proyecto1.vistas.*;

public class MenuAdministradorFrame extends JFrame {
        private Administrador administrador; // ← nueva referencia
        private List<Persona> listaUsuarios;

        public MenuAdministradorFrame(List<Persona> listaUsuarios) {
                this.administrador = new Administrador();
                this.listaUsuarios = administrador.getListaUsuarios();
                // --- Configuración de la Ventana ---
                setTitle("Menú Principal de Administradores");
                setSize(600, 400);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana, no la app
                setLocationRelativeTo(null);

                // --- Panel Principal ---
                JPanel panel = new JPanel(new BorderLayout());

                // --- Etiqueta de Bienvenida ---
                JLabel welcomeLabel = new JLabel("Bienvenido(a), Administrador" , SwingConstants.CENTER);
                welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
                panel.add(welcomeLabel, BorderLayout.NORTH);

                // --- Creación de la Barra de Menú ---
                JMenuBar menuBar = new JMenuBar();

                // 1. Menú "Estudiantes"
                JMenu estudiantesMenu = new JMenu("Estudiantes");
                estudiantesMenu.setMnemonic(KeyEvent.VK_E); // Atajo: Alt + E

                JMenuItem agregarEstudianteItem = new JMenuItem("Agregar");
                JMenuItem consultarEstudianteItem = new JMenuItem("Consultar");
                JMenuItem modificarEstudianteItem = new JMenuItem("Modificar");
                JMenuItem eliminarEstudianteItem = new JMenuItem("Eliminar");

                // Añadir acciones (por ahora solo muestran un mensaje)
                agregarEstudianteItem.addActionListener(e -> {
                VentanaInsertarEstudiante dialog = new VentanaInsertarEstudiante(MenuAdministradorFrame.this);
                dialog.setVisible(true);
                if (dialog.isConfirmado()) {
                        Estudiante nuevo = dialog.getEstudiante();
                        // Registrar usando el administrador
                        administrador.registrarEstudiante(
                        nuevo.getNombre(),
                        nuevo.getApellido1(),
                        nuevo.getApellido2(),
                        nuevo.getIdentificacionPersonal(),
                        nuevo.getTelefono(),
                        nuevo.getCorreoElectronico(),
                        nuevo.getDireccionFisica(),
                        nuevo.getContrasena(),
                        nuevo.getOrgLabora(),
                        nuevo.getTemasInteres()
                        );
                        JOptionPane.showMessageDialog(this, "Estudiante registrado exitosamente.");
                }
                });
                consultarEstudianteItem.addActionListener(e -> {
                        String id = JOptionPane.showInputDialog(this, "Ingrese el ID del estudiante:");
                        if (id != null && !id.trim().isEmpty()) {
                        Estudiante est = administrador.obtenerEstudiantePorId(id.trim());
                        if (est != null) {
                                String temasStr = String.join(", ", est.getTemasInteres());
                                // Mostrar info en un JOptionPane o en una nueva ventana
                                JOptionPane.showMessageDialog(this,
                                "Estudiante encontrado:\n" +
                                "Nombre: " + est.getNombre() + " " + est.getApellido1() + " " + est.getApellido2() + "\n" +
                                "ID: " + est.getIdentificacionPersonal() + "\n" +
                                "Telefono: " + est.getTelefono() + "\n" +
                                "Correo Electronico: " + est.getCorreoElectronico() + "\n" +
                                "Direccion Fisica:  " + est.getDireccionFisica() + "\n" +
                                "Organización: " + est.getOrgLabora() + "\n" + 
                                "Temas de interes: " + temasStr + "\n",

                                "Consulta Exitosa",
                                JOptionPane.INFORMATION_MESSAGE);
                        } else {
                                JOptionPane.showMessageDialog(this,
                                "Estudiante con ID '" + id + "' no encontrado.",
                                "No encontrado",
                                JOptionPane.WARNING_MESSAGE);
                        }
                        }
                });
                modificarEstudianteItem.addActionListener(e -> {
                String id = JOptionPane.showInputDialog(
                        MenuAdministradorFrame.this,
                        "Ingrese el ID del estudiante a modificar:"
                );
                if (id == null || id.trim().isEmpty()) return;

                Estudiante est = administrador.obtenerEstudiantePorId(id.trim());
                if (est == null) {
                        JOptionPane.showMessageDialog(
                        MenuAdministradorFrame.this,
                        "Estudiante con ID '" + id + "' no encontrado.",
                        "No encontrado",
                        JOptionPane.WARNING_MESSAGE
                        );
                        return;
                }

                VentanaEditarEstudiante dialog = new VentanaEditarEstudiante(MenuAdministradorFrame.this, est);
                dialog.setVisible(true);

                if (dialog.isConfirmado()) {
                        // Modificar datos comunes (heredados de Persona)
                        administrador.modificarDatosComunes(
                        dialog.getIdentificacion(),
                        dialog.getNombre(),
                        dialog.getApellido1(),
                        dialog.getApellido2(),
                        dialog.getTelefono(),
                        dialog.getCorreo(),
                        dialog.getDireccion()
                        );

                        // Modificar datos específicos de estudiante
                        administrador.modificarEstudiante(
                        dialog.getIdentificacion(),
                        dialog.getOrgLaboral(),
                        dialog.getTemasInteres()
                        );

                        JOptionPane.showMessageDialog(
                        MenuAdministradorFrame.this,
                        "Estudiante actualizado exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                        );
                }
                });
                eliminarEstudianteItem.addActionListener(e -> {
                String id = JOptionPane.showInputDialog(
                        MenuAdministradorFrame.this,
                        "Ingrese el ID del estudiante a eliminar:"
                );
                if (id == null || id.trim().isEmpty()) return;

                // Verificar que exista
                if (administrador.obtenerEstudiantePorId(id.trim()) == null) {
                        JOptionPane.showMessageDialog(
                        MenuAdministradorFrame.this,
                        "Estudiante con ID '" + id + "' no encontrado.",
                        "No encontrado",
                        JOptionPane.WARNING_MESSAGE
                        );
                        return;
                }

                // Confirmar eliminación
                int confirm = JOptionPane.showConfirmDialog(
                        MenuAdministradorFrame.this,
                        "¿Está seguro de eliminar al estudiante con ID: " + id + "?\n" +
                        "Esta acción no se puede deshacer.",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                        administrador.eliminarUsuario(id.trim());
                        JOptionPane.showMessageDialog(
                        MenuAdministradorFrame.this,
                        "Estudiante eliminado exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                        );
                }
                });

                estudiantesMenu.add(agregarEstudianteItem);
                estudiantesMenu.add(consultarEstudianteItem);
                estudiantesMenu.add(modificarEstudianteItem);
                estudiantesMenu.add(eliminarEstudianteItem);

                // 2. Menú "Profesores"
                JMenu profesoresMenu = new JMenu("Profesores");
                profesoresMenu.setMnemonic(KeyEvent.VK_P); // Atajo: Alt + P

                JMenuItem agregarProfesorItem = new JMenuItem("Agregar");
                JMenuItem consultarProfesorItem = new JMenuItem("Consultar");
                JMenuItem modificarProfesorItem = new JMenuItem("Modificar");
                JMenuItem eliminarProfesorItem = new JMenuItem("Eliminar");

                agregarProfesorItem.addActionListener(e -> {
                VentanaInsertarProfesor dialog = new VentanaInsertarProfesor(MenuAdministradorFrame.this);
                dialog.setVisible(true);
                
                if (dialog.isConfirmado()) {
                        Profesor nuevo = dialog.getProfesor();
                        administrador.registrarProfesor(
                        nuevo.getNombre(),
                        nuevo.getApellido1(),
                        nuevo.getApellido2(),
                        nuevo.getIdentificacionPersonal(),
                        nuevo.getTelefono(),
                        nuevo.getCorreoElectronico(),
                        nuevo.getDireccionFisica(),
                        nuevo.getContrasena(),
                        nuevo.getTitulosObtenidos(),
                        nuevo.getCertificacionesEstudios()
                        );
                        JOptionPane.showMessageDialog(
                        MenuAdministradorFrame.this,
                        "Profesor registrado exitosamente con ID: " + nuevo.getIdentificacionPersonal(),
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                        );
                }
                });
                consultarProfesorItem.addActionListener(e -> {
                String id = JOptionPane.showInputDialog(this, "Ingrese el ID del profesor:");
                if (id != null && !id.trim().isEmpty()) {
                        Profesor prof = administrador.obtenerProfesorPorId(id.trim());
                        if (prof != null) {
                        // Convertir listas a cadenas legibles
                        String titulosStr = String.join(", ", prof.getTitulosObtenidos());
                        String certificacionesStr = String.join(", ", prof.getCertificacionesEstudios());

                        JOptionPane.showMessageDialog(this,
                                "Profesor encontrado:\n" +
                                "Nombre: " + prof.getNombre() + " " + prof.getApellido1() + " " + prof.getApellido2() + "\n" +
                                "ID: " + prof.getIdentificacionPersonal() + "\n" +
                                "Teléfono: " + prof.getTelefono() + "\n" +
                                "Correo Electrónico: " + prof.getCorreoElectronico() + "\n" +
                                "Dirección Física: " + prof.getDireccionFisica() + "\n" +
                                "Títulos Obtenidos: " + (titulosStr.isEmpty() ? "Ninguno" : titulosStr) + "\n" +
                                "Certificaciones: " + (certificacionesStr.isEmpty() ? "Ninguna" : certificacionesStr),
                                "Consulta Exitosa",
                                JOptionPane.INFORMATION_MESSAGE);
                        } else {
                        JOptionPane.showMessageDialog(this,
                                "Profesor con ID '" + id + "' no encontrado.",
                                "No encontrado",
                                JOptionPane.WARNING_MESSAGE);
                        }
                }
                });
                modificarProfesorItem.addActionListener(e -> {
                String id = JOptionPane.showInputDialog(
                        MenuAdministradorFrame.this,
                        "Ingrese el ID del profesor a modificar:"
                );
                if (id == null || id.trim().isEmpty()) return;

                Profesor prof = administrador.obtenerProfesorPorId(id.trim());
                if (prof == null) {
                        JOptionPane.showMessageDialog(
                        MenuAdministradorFrame.this,
                        "Profesor con ID '" + id + "' no encontrado.",
                        "No encontrado",
                        JOptionPane.WARNING_MESSAGE
                        );
                        return;
                }

                VentanaEditarProfesor dialog = new VentanaEditarProfesor(MenuAdministradorFrame.this, prof);
                dialog.setVisible(true);

                if (dialog.isConfirmado()) {
                        administrador.modificarProfesor(
                        dialog.getIdentificacion(),
                        dialog.getTitulos(),
                        dialog.getCertificaciones()
                        );
                        // También modificar datos comunes
                        administrador.modificarDatosComunes(
                        dialog.getIdentificacion(),
                        dialog.getNombre(),
                        dialog.getApellido1(),
                        dialog.getApellido2(),
                        dialog.getTelefono(),
                        dialog.getCorreo(),
                        dialog.getDireccion()
                        );
                        JOptionPane.showMessageDialog(
                        MenuAdministradorFrame.this,
                        "Profesor actualizado exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                        );
                }
                });
               eliminarProfesorItem.addActionListener(e -> {
                String id = JOptionPane.showInputDialog(
                        MenuAdministradorFrame.this,
                        "Ingrese el ID del profesor a eliminar:"
                );
                if (id == null || id.trim().isEmpty()) return;

                if (administrador.obtenerProfesorPorId(id.trim()) == null) {
                        JOptionPane.showMessageDialog(
                        MenuAdministradorFrame.this,
                        "Profesor con ID '" + id + "' no encontrado.",
                        "No encontrado",
                        JOptionPane.WARNING_MESSAGE
                        );
                        return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                        MenuAdministradorFrame.this,
                        "¿Está seguro de eliminar al profesor con ID: " + id + "?\n" +
                        "Esta acción no se puede deshacer.",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                        administrador.eliminarUsuario(id.trim());
                        JOptionPane.showMessageDialog(
                        MenuAdministradorFrame.this,
                        "Profesor eliminado exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                        );
                }
                });
                profesoresMenu.add(agregarProfesorItem);
                profesoresMenu.add(consultarProfesorItem);
                profesoresMenu.add(modificarProfesorItem);
                profesoresMenu.add(eliminarProfesorItem);

                // 3. Menú "Cursos" (sin submenús por ahora)
               // 3. Menú "Cursos" (con submenús)
                JMenu cursosMenu = new JMenu("Cursos");
                cursosMenu.setMnemonic(KeyEvent.VK_C);

                JMenuItem agregarCursoItem = new JMenuItem("Agregar");
                JMenuItem consultarCursoItem = new JMenuItem("Consultar");
                JMenuItem modificarCursoItem = new JMenuItem("Modificar");
                JMenuItem eliminarCursoItem = new JMenuItem("Eliminar");

                // Acción para CONSULTAR curso
                consultarCursoItem.addActionListener(e -> {
                String id = JOptionPane.showInputDialog(this, "Ingrese el ID del curso:");
                if (id != null && !id.trim().isEmpty()) {
                        Curso curso = administrador.obtenerCursoPorId(id.trim());
                        if (curso != null) {
                        JOptionPane.showMessageDialog(this,
                                "Curso encontrado:\n" +
                                "ID: " + curso.getIdCurso() + "\n" +
                                "Nombre: " + curso.getNombreCurso() + "\n" +
                                "Descripción: " + curso.getDescripcionCurso() + "\n" +
                                "Horas por día: " + curso.getCantHorasDia() + "\n" +
                                "Modalidad: " + curso.getModalidad() + "\n" +
                                "Mín. estudiantes: " + curso.getCantMinEstudiantes() + "\n" +
                                "Máx. estudiantes: " + curso.getCantMaxEstudiantes() + "\n" +
                                "Calificación mínima para aprobar: " + curso.getCalificacionMinAprobar() + "\n" +
                                "Tipo de curso: " + curso.getTipoCurso(),
                                "Consulta de Curso",
                                JOptionPane.INFORMATION_MESSAGE);
                        } else {
                        JOptionPane.showMessageDialog(this,
                                "Curso con ID '" + id + "' no encontrado.",
                                "No encontrado",
                                JOptionPane.WARNING_MESSAGE);
                        }
                }
                });

                agregarCursoItem.addActionListener(e -> {
                VentanaInsertarCurso dialog = new VentanaInsertarCurso(MenuAdministradorFrame.this);
                dialog.setVisible(true);
                
                if (dialog.isConfirmado()) {
                        Curso nuevo = dialog.getCurso();
                        administrador.agregarCurso(
                        nuevo.getIdCurso(),
                        nuevo.getNombreCurso(),
                        nuevo.getDescripcionCurso(),
                        nuevo.getCantHorasDia(),
                        nuevo.getModalidad(),
                        nuevo.getCantMinEstudiantes(),
                        nuevo.getCantMaxEstudiantes(),
                        nuevo.getCalificacionMinAprobar(),
                        nuevo.getTipoCurso()
                        );
                        JOptionPane.showMessageDialog(
                        MenuAdministradorFrame.this,
                        "Curso registrado exitosamente con ID: " + nuevo.getIdCurso(),
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                        );
                }
                });
                modificarCursoItem.addActionListener(e -> {
                String id = JOptionPane.showInputDialog(
                        MenuAdministradorFrame.this,
                        "Ingrese el ID del curso a modificar:"
                );
                if (id == null || id.trim().isEmpty()) return;

                Curso curso = administrador.obtenerCursoPorId(id.trim());
                if (curso == null) {
                        JOptionPane.showMessageDialog(
                        MenuAdministradorFrame.this,
                        "Curso con ID '" + id + "' no encontrado.",
                        "No encontrado",
                        JOptionPane.WARNING_MESSAGE
                        );
                        return;
                }

                VentanaEditarCurso dialog = new VentanaEditarCurso(MenuAdministradorFrame.this, curso);
                dialog.setVisible(true);

                if (dialog.isConfirmado()) {
                        administrador.modificarCurso(
                        dialog.getIdCurso(),
                        dialog.getNombreCurso(),
                        dialog.getDescripcionCurso(),
                        dialog.getCantHorasDia(),
                        dialog.getModalidad(),
                        dialog.getCantMinEstudiantes(),
                        dialog.getCantMaxEstudiantes(),
                        dialog.getCalificacionMinAprobar(),
                        dialog.getTipoCurso()
                        );
                        JOptionPane.showMessageDialog(
                        MenuAdministradorFrame.this,
                        "Curso actualizado exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                        );
                }
                });
                eliminarCursoItem.addActionListener(e -> {
                String id = JOptionPane.showInputDialog(
                        MenuAdministradorFrame.this,
                        "Ingrese el ID del curso a eliminar:"
                );
                if (id == null || id.trim().isEmpty()) return;

                if (administrador.obtenerCursoPorId(id.trim()) == null) {
                        JOptionPane.showMessageDialog(
                        MenuAdministradorFrame.this,
                        "Curso con ID '" + id + "' no encontrado.",
                        "No encontrado",
                        JOptionPane.WARNING_MESSAGE
                        );
                        return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                        MenuAdministradorFrame.this,
                        "¿Está seguro de eliminar el curso con ID: " + id + "?\n" +
                        "Esta acción no se puede deshacer.",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                        administrador.eliminarCurso(id.trim());
                        JOptionPane.showMessageDialog(
                        MenuAdministradorFrame.this,
                        "Curso eliminado exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                        );
                }
                });
                cursosMenu.add(agregarCursoItem);
                cursosMenu.add(consultarCursoItem);
                cursosMenu.add(modificarCursoItem);
                cursosMenu.add(eliminarCursoItem);
                // 4. Menú "Asociar Grupos-Cursos"
                JMenu asociarGruposCursosMenu = new JMenu("Asociar Grupos-Cursos");
                asociarGruposCursosMenu.addActionListener(e -> JOptionPane.showMessageDialog(this,
                                "Opción 'Asociar Grupos-Cursos' seleccionada."));

                // 5. Menú "Asociar Grupos-Profesores"
                JMenu asociarGruposProfesoresMenu = new JMenu("Asociar Grupos-Profesores");
                asociarGruposProfesoresMenu.addActionListener(e -> JOptionPane.showMessageDialog(this,
                                "Opción 'Asociar Grupos-Profesores' seleccionada."));

                // 6. Menú "Reportes"
                JMenu reportesMenu = new JMenu("Reportes");
                reportesMenu.setMnemonic(KeyEvent.VK_R); // Atajo: Alt + R

                JMenuItem listaMatriculaItem = new JMenuItem("Lista de Matrícula");
                JMenuItem estadisticaMatriculaItem = new JMenuItem("Estadística de matrícula");

                listaMatriculaItem.addActionListener(
                                e -> JOptionPane.showMessageDialog(this, "Opción 'Lista de Matrícula' seleccionada."));
                estadisticaMatriculaItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
                                "Opción 'Estadística de matrícula' seleccionada."));

                reportesMenu.add(listaMatriculaItem);
                reportesMenu.add(estadisticaMatriculaItem);

                // --- Añadir todos los menús a la barra de menú ---
                menuBar.add(estudiantesMenu);
                menuBar.add(profesoresMenu);
                menuBar.add(cursosMenu);
                menuBar.add(asociarGruposCursosMenu);
                menuBar.add(asociarGruposProfesoresMenu);
                menuBar.add(reportesMenu);

                // --- Establecer la barra de menú en la ventana ---
                this.setJMenuBar(menuBar);

                // --- Botón de Cerrar Sesión ---
                JButton logoutButton = new JButton("Cerrar Sesión");
                logoutButton.addActionListener(e -> {
                        // 1. Cierra la ventana del menú actual
                        dispose();
                        // 2. Crea una nueva ventana de login, pasándole la lista de usuarios
                        SwingUtilities.invokeLater(() -> {
                                LoginFrame loginFrame = new LoginFrame(listaUsuarios);
                                loginFrame.setVisible(true);
                        });
                });
                panel.add(logoutButton, BorderLayout.SOUTH);

                // --- Añadir el panel a la ventana y hacerla visible ---
                add(panel);
                setVisible(true);
        }
    }