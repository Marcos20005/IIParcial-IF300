<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Nuevo caso</title>
    <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
    <div class="formulario-detalle ventana" style="display: block;">
        <h2>Registre la información solicitada</h2>

        <!-- Fecha y hora actual de la denuncia se mostrarán dinámicamente -->
        <div class="fila-encabezado">
            <div>
                <p><strong>Fecha de la denuncia:</strong> <span id="fechaDenuncia"></span></p>
                <p><strong>Hora de la denuncia:</strong> <span id="horaDenuncia"></span></p>
                <p><strong>Información de la víctima</strong></p>
            </div>
        </div>

        <!-- Formulario para registrar un nuevo caso de violencia -->
        <form action="GuardarCaso.jsp" method="post">
            <div class="fila-doble">
                <div class="columna">
                    <label>Descripción de la denuncia *</label>
                    <textarea rows="4" name="txtDescripcionDenuncia" required></textarea> 

                    <label>Nombre de la persona *</label>
                    <input type="text" name="txtNombre" required> 

                    <label>Edad de la persona *</label>
                    <input type="number" name="txtedad" required>

                    <label>Dirección de la persona *</label>
                    <input type="text" name="txtDireccion" required>

                    <label>Ocupación de la persona *</label>
                    <input type="text" name="txtOcupacion" required>

                    <label>Estado civil de la persona *</label>
                    <select required name="boxEstado">
                        <option value="">Seleccione...</option>
                        <option value="Soltero/a">Soltero/a</option>
                        <option value="Casado/a">Casado/a</option>
                        <option value="Divorciado/a">Divorciado/a</option>
                        <option value="Separado/a">Separado/a</option>
                        <option value="Viudo/a">Viudo/a</option>
                        <option value="Concubinato">Concubinato</option>
                    </select>
                </div>

                <div class="columna">
                    <label>Cédula de la persona *</label>
                    <input type="text" name="txtCedula" required>

                    <label>Número celular de la persona *</label>
                    <input type="text" name="txtCelular" required>

                    <label>Género de la persona *</label>
                    <div class="radio-grupo">
                        <label><input type="radio" name="genero" value="Femenino" required> Femenino</label>
                        <label><input type="radio" name="genero" value="Masculino" required> Masculino</label>
                        <label><input type="radio" name="genero" value="Otro" required> Otro</label>
                    </div>

                    <label>Nacionalidad de la persona *</label>
                    <input type="text" name="txtNacionalidad" required>

                    <label>Tipo de violencia *</label>
                    <select required name="tipoViolencia">
                        <option value="">Seleccione...</option>
                        <option value="Violencia Digital">Violencia Digital</option>
                        <option value="Violencia Económica">Violencia Económica</option>
                        <option value="Violencia Emocional">Violencia Emocional</option>
                        <option value="Violencia Física">Violencia Física</option>
                        <option value="Violencia Sexual">Violencia Sexual</option>
                    </select>
                </div>
            </div>

            <div class="botones">
                <button type="submit"><i class="fi fi-rr-disk"></i>Ingresar</button> 
            </div>
        </form>
    </div>

    <script>
        // se encarga de mostrar la fecha
        const ahora = new Date();
        document.getElementById("fechaDenuncia").textContent = ahora.toLocaleDateString();
        document.getElementById("horaDenuncia").textContent = ahora.toLocaleTimeString();
    </script>
</body>
</html>
