package es.ciudadescolar.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.instituto.Alumno;

public class DbManager 
{
    private static final Logger LOG = LoggerFactory.getLogger(DbManager.class);
    private static final String DRIVER = "driver";
    private static final String URL = "url";
    private static final String USUARIO = "user";
    private static final String PWD = "password";

    private Connection con = null;
    
    public DbManager()
    {
        Properties prop = new Properties();

        try 
        {
            prop.load(new FileInputStream("conexionBD.properties"));   
        
            // registramos driver (en versiones actuales no es necesario)
            Class.forName(prop.getProperty(DRIVER));
            //con = DriverManager.getConnection("jdbc:mysql://192.168.203.77:3306/dam2_2425", "dam2", "dam2");
            con = DriverManager.getConnection(prop.getProperty(URL), prop.getProperty(USUARIO),prop.getProperty(PWD));
            LOG.debug("Establecida conexión satisfactoriamente");
        }
        catch (IOException e) 
        {
            LOG.error("Imposible cargar propiedades de la conexión");
        }
        catch (ClassNotFoundException e) 
        {
            LOG.error("Registro de driver con error: "+ e.getMessage());
        } catch (SQLException e) 
        {
            LOG.error("Imposible establecer conexion con la BD: "+e.getMessage());
        }
    }

    public List<Alumno> getAllAlumnos()
    {
        List<Alumno> alumnos = null;

        Statement stAlumnos = null;

        ResultSet rstAlumno = null;

        Alumno alumno =null;

        if (con != null)
        {
            try 
            {
                stAlumnos = con.createStatement();
                rstAlumno = stAlumnos.executeQuery(SQL.RECUPERA_ALUMNOS);
                
                if (rstAlumno.next()) // if(rstAlumno.first())
                {
                    alumnos = new ArrayList<Alumno>();
                    do
                    {
                        alumno = new Alumno();
                        alumno.setExpediente(Integer.valueOf(rstAlumno.getInt(1)));
                        alumno.setNombre(rstAlumno.getString(2));
                        
                        Date fecha = rstAlumno.getDate(3);
                        if (fecha != null)
                            alumno.setFecha_nac(fecha.toLocalDate());

                        alumnos.add(alumno);
                    }while(rstAlumno.next());
                }
                
                LOG.debug("Se ha ejecutado correctamente la sentencia SELECT");
            }
            catch (SQLException e) 
            {
                LOG.error("Imposible consultar alumnos: "+e.getMessage());
            }
            finally
            {
                try 
                {
                    if (rstAlumno != null) 
                        rstAlumno.close();
                    if (stAlumnos != null)
                        stAlumnos.close();
                } catch (SQLException e) 
                {
                    LOG.error("Error durante el cierre de la conexión");
                }
            }
        }
        return alumnos;
        
    }
    public boolean cerrarBd()
    {
        boolean status = false;

        if (con != null)
        {
            try 
            {
                con.close();
                LOG.debug("Cerrada conexión satisfactoriamente");
                status = true;
            } 
            catch (SQLException e) 
            {
                LOG.error("Error cerrando la conexion: "+e.getMessage());
            }
        }
        return status;
    }

    public Alumno getAlumnoPorExpYNom(int exped, String nombre)
    {
        Alumno al = null;
        PreparedStatement pstAlumno =null;
        ResultSet rstAlumno = null;
        // al realizar la busqueda por PK, o me devuelve uno o ninguno (nunca varios)
        try 
        {
            pstAlumno = con.prepareStatement(SQL.RECUPERA_ALUMNO_EXP);
            pstAlumno.setInt(1,exped);
            pstAlumno.setString(2,nombre);

            rstAlumno = pstAlumno.executeQuery();

            if (rstAlumno.next())
            {
                al = new Alumno(rstAlumno.getInt(1),rstAlumno.getString(2), rstAlumno.getDate(3));
                LOG.debug("Recuperado alumno exp["+exped+"] y nombre["+nombre+"]");
            }

        } 
        catch (SQLException e) 
        {
            LOG.error("Error durante la consulta parametrizada: "+ e.getMessage());
        }
        finally
        {
                try 
                {

                    if (rstAlumno != null)
                    {
                        rstAlumno.close();
                        LOG.debug("Se ha cerrado correctamente el resultSet");
                    }
                    if (pstAlumno != null)
                     {
                        pstAlumno.close();
                        LOG.debug("Se ha cerrado correctamente el statement");
                     }  
                } catch (SQLException e) 
                {
                    LOG.error("Error liberando recursos de la consulta parametrizada");    
                }
        }

        return al;
    }

    public boolean altaDeAlumno(Alumno al)
    {
        boolean status = false;
        PreparedStatement pstNuevoAlumno = null;


        if (con != null)
        {
            try 
            {
                pstNuevoAlumno = con.prepareStatement(SQL.ALTA_NUEVO_ALUMNO);
                pstNuevoAlumno.setInt(1, al.getExpediente());
                pstNuevoAlumno.setString(2,al.getNombre());
                pstNuevoAlumno.setDate(3,Date.valueOf(al.getFecha_nac()));
                if (pstNuevoAlumno.executeUpdate() == 1)
                {
                    LOG.debug("Insercción realizada correctamente: Alumno ["+al.getExpediente()+"]");
                    status = true;
                }
            }
             catch (SQLException e) 
            {
                LOG.error("Error durante el alta del alumno: "+ e.getMessage());    
            }
            finally
            {
                if (pstNuevoAlumno != null)
                {
                    try 
                    {
                        pstNuevoAlumno.close();
                        LOG.debug("La liberación de recursos ha ido bien");
                    } catch (SQLException e) 
                    {
                        LOG.error("Error liberando recursos de la inserción parametrizada");  
                    }

                }
            }

        }

        return status;
    }

    public boolean modificarNombreDeAlumno(Alumno al)
    {
        boolean status = false;
        PreparedStatement pstCambioNombreAlumno = null;


        if (con != null)
        {
            try 
            {
                pstCambioNombreAlumno = con.prepareStatement(SQL.CAMBIO_NOMBRE_ALUMNO);
                pstCambioNombreAlumno.setString(1, al.getNombre());
                pstCambioNombreAlumno.setInt(2,al.getExpediente());

                if (pstCambioNombreAlumno.executeUpdate() == 1)
                {
                    LOG.debug("Actualización realizada correctamente: Alumno ["+al.getExpediente()+"]");
                    status = true;
                }
            }
             catch (SQLException e) 
            {
                LOG.error("Error durante la actualización del alumno: "+ e.getMessage());    
            }
            finally
            {
                if (pstCambioNombreAlumno != null)
                {
                    try 
                    {
                        pstCambioNombreAlumno.close();
                        LOG.debug("La liberación de recursos ha ido bien");
                    } catch (SQLException e) 
                    {
                        LOG.error("Error liberando recursos de la inserción parametrizada");  
                    }

                }
            }

        }

        return status;
    }

     public boolean borrarAlumno(int expediente)
    {
        boolean status = false;
        PreparedStatement pstBorradoAlumno = null;


        if (con != null)
        {
            try 
            {
                pstBorradoAlumno = con.prepareStatement(SQL.BAJA_ALUMNO);
                 pstBorradoAlumno.setInt(1,expediente);

                if (pstBorradoAlumno.executeUpdate() == 1)
                {
                    LOG.debug("Borrado realizada correctamente: Alumno ["+expediente+"]");
                    status = true;
                }
            }
             catch (SQLException e) 
            {
                LOG.error("Error durante borrado del alumno: "+ e.getMessage());    
            }
            finally
            {
                if (pstBorradoAlumno != null)
                {
                    try 
                    {
                        pstBorradoAlumno.close();
                        LOG.debug("La liberación de recursos ha ido bien");
                    } catch (SQLException e) 
                    {
                        LOG.error("Error liberando recursos del borrado parametrizada");  
                    }

                }
            }

        }

        return status;
    }

    public boolean muestraAlumno(int expediente)
    {
        boolean status =false;
        CallableStatement cs = null;
        
        if (con != null)
        {
            try 
            {
                cs = con.prepareCall(SQL.INVOCACION_SP_INFO_ALUMNO);
                cs.setInt(1,expediente);

                cs.execute();
                LOG.debug("Invocación a SP satisfactoria ["+SQL.INVOCACION_SP_INFO_ALUMNO+"] con el parámetro ["+expediente+"]");
                status = true;
            } 
            catch (SQLException e) 
            {
                LOG.error("Error durante la invocación del SP :"+e.getMessage());
            }
            finally
            {
                if (cs != null)
                {
                    try 
                    {
                        cs.close();
                        LOG.debug("Cierre de recursos realizado correctamente");
                    } 
                    catch (SQLException e) 
                    {
                        LOG.error("Imposible cerrar CallableStatement");
                    }

                }
            }

        }

        return status;
    }


    public int recuperaNumAlumnosSP()
    {
        CallableStatement cs = null;
        int numAlumnos = -1;

        if (con != null)
        {
            try 
            {
                cs = con.prepareCall(SQL.INVOCACION_SP_GET_NUM_ALUMNOS);

                // registramos el parámetro como DE SALIDA para luego poder recuperar su valor tras la ejecución
                cs.registerOutParameter(1, Types.INTEGER);
                
                cs.execute();
                numAlumnos = cs.getInt(1);

                LOG.debug("Invocación a SP satisfactoria ["+SQL.INVOCACION_SP_GET_NUM_ALUMNOS+"] con el parámetro de salida ["+numAlumnos+"]");
            } 
            catch (SQLException e) 
            {
                LOG.error("Error durante la invocación del SP :"+e.getMessage());
            }
            finally
            {
                if (cs != null)
                {
                    try 
                    {
                        cs.close();
                        LOG.debug("Cierre de recursos realizado correctamente");
                    } 
                    catch (SQLException e) 
                    {
                        LOG.error("Imposible cerrar CallableStatement");
                    }

                }
            }

        }
        return numAlumnos;

    }

    public int getNotaAlumno(int exp)
    {
        int nota = 0; // raices no permite nota 0
        CallableStatement csFun = null;

        if (con != null)
        {
            try 
            {
                csFun = con.prepareCall(SQL.INVOCACION_FUNC_GET_NOTA_ALUMNO);
                // Como las funciones devuelven un valor, para poder acceder a él una vez ejecutada, tengo que registrarlo como salida
                csFun.registerOutParameter(1, Types.INTEGER);
                // Como esta función además tiene un parámetro de entrada, le fijo el valor que yo necesito 
                csFun.setInt(2,exp);

                //ejecuto la función
                csFun.execute();
                LOG.debug("Invocación a función realizada correctamente");

                // tras la ejecución puedo recuperar el valor de los parámetros de salida , en este caso el valor que devuelve la func
                nota = csFun.getInt(1);

                LOG.debug("Recuperada nota [" + nota +"] para el alumno ["+exp+"]");
            } 
            catch (SQLException e) 
            {
                LOG.error("Error durante la invocación a función de nota alumno ["+exp+"]: "+ e.getMessage());
            }
            finally
            {
                if (csFun != null)
                {
                    try 
                    {
                        csFun.close();
                        LOG.debug("La liberación de recursos en la invocación de la función ha sido satisfactoria");
                    } catch (SQLException e) {
                        LOG.error("Error durante la liberación de recursos en la invocación de la función :" +e.getMessage());
                    }

                }
            }

        }
        return nota;
    }
}
