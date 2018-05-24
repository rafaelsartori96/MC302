/*
 * Respostas:
 * 
 * *** Obs: As questões ficaram confusas mesmo, então qualquer interpretação é válida 
 * *** (dado uma resposta certa na interpretação).
 * 
 * 1 - Em geral fazer algo assim não faz sentido, mas uma opção é ter um método em GrupoPublico que retorna um
 *  GrupoPrivado, que tem todas as informações de Grupo, e possivelmente valores default em campos diferentes.
 * 
 * 2 - Uma interpretação é de que a variável é do tipo Grupo, mas o objeto criado é do tip GrupoPublico. Nesse caso,
 *  não seria possível acessar o método diretamente, já que o Grupo não sabe da existência desse método se ele não
 *  foi declarado como abstract. Uma opção seria fazer downcast de Grupo para GrupoPublico.
 *  
 * 3 - Não seria possível de forma alguma. E a alternativa depende muito do porque algo assim seria desejável, 
 *  um exemplo seria ter acesso a um objeto do grupoPrivado que você quisesse acessar esse método.
 *  
 * 4 - Dentre as vantagens está o fato de que algumas classes realmente não são pra serem usadas diretamente. No caso
 *  de grupo e carona, por exemplo, estes precisam ser necessariamente ou publico ou privado. Não faz sentido semanticamente
 *  instanciar uma Carona, ou um Grupo. Colocar abstract impede de instanciar, mas ainda ganhando as vantagens de usar
 *  herança. Se o conceito for aplicado corretamente, não acredito que haja desvantagem. Se aplicado incorretamente,
 *  talvez seja desejável ainda instanciar objetos da classe pai.
 *  
 * 5 - Sobrecarregar métodos não está relacionado com herança, então é perfeitamente possível em qualquer classe.
 *  Já sobrescrita só faz sentido em herança, mesmo.
 */

package inicio;

import carona.Carona;
import caronante.Caronante;
import caroneiro.Caroneiro;
import grupo.Grupo;
import grupo.GrupoPrivado;
import grupo.GrupoPublico;
import pagamento.MetodoPagamento;
import perfil.Perfil;
import usuario.Usuario;

public class Lab7 {

	public static void main(String[] args) {
		Caronante caronante1 = new Caronante(5, "Folk", "abcd-123", "abdef12345",
				"Alguma marca", "Algum modelo", 3);
		Caronante caronante2 = new Caronante(50, "Chamber pop", "ab2cd-123", "abde4f12345",
				"Alguma outra marca", "Algum outro modelo", 5);
		Caroneiro caroneiro1 = new Caroneiro(true);
		Caroneiro caroneiro2 = new Caroneiro("5435345345");
		
		Perfil perfilCaronante1 = new Perfil('f',"01/01/2001", "Campinas", "SP", "123123", true, caronante1, null, null);		
		Usuario usuario1 = new Usuario("Joanna", "jnewsom@dunno.com", "sadie", true, perfilCaronante1);
		perfilCaronante1.setUsuario(usuario1);
		
		Perfil perfilCaronante2 = new Perfil('m',"01/01/1999", "Sorocaba", "SP", "123123", true, caronante2, null, null);		
		Usuario usuario2 = new Usuario("Sufjan", "sufjan@dunno.com", "wasps", true, perfilCaronante2);
		perfilCaronante2.setUsuario(usuario2);
		
		Perfil perfilCaroneiro2 = new Perfil('m',"01/01/1990", "Sao Paulo", "SP", "123123", true, null, caroneiro1, null);		
		Usuario usuario3 = new Usuario("Shinji", "hikari@dunno.com", "something", true, perfilCaroneiro2);
		perfilCaroneiro2.setUsuario(usuario3);
		
		Perfil perfilCaroneiro3 = new Perfil('f',"01/01/1999", "Belo Horizonte", "MG", "123123", true, null, caroneiro2, null);		
		Usuario usuario4 = new Usuario("Lily Briscoe", "lily@dunno.com", "ramsay", true, perfilCaroneiro3);
		perfilCaroneiro3.setUsuario(usuario4);
		
		Grupo grupoPrivado = new GrupoPrivado("Pilotos de robos", "Hm", usuario3);
		grupoPrivado.adicionarMembro(usuario1);
		
		Grupo grupoPublico = new GrupoPublico("Pintores", "Alguem?", usuario4);
		grupoPublico.adicionarMembro(usuario2);
		
		//O caronante 1 e 2 pertencem a um grupo privado e um publico, respectivamente
		Carona caronaPrivada = caronante1.oferecerCaronaPrivada(13.2, 15.46, 13.45, 15.23, "15:05:00 10/10/2018", 20.50f);
		Carona caronaPublica = caronante2.oferecerCaronaPublic(14.2, 16.46, 14.45, 16.23, "16:06:00 11/11/2018", 10.50f);
		
		caroneiro1.pedirCarona(caronaPrivada);
		caroneiro2.pedirCarona(caronaPublica);
		
		caronaPrivada.adicionarFormaPagamento(MetodoPagamento.Gratis);
		caronaPublica.adicionarFormaPagamento(MetodoPagamento.Dinheiro);
		
		System.out.println("\n------------------------\n");
		System.out.println("Testando a atribuição de avaliacoes da carona privada");
		System.out.println("Caronante dando a nota: " + caronaPrivada.atribuirNotaCaronante(8));
		System.out.println("Caroneiro 2 dando a nota: " + caronaPrivada.atribuirNotaCaroneiro(2, 3));
		
		System.out.println("\n------------------------\n");
		System.out.println("Testando a atribuição de avaliacoes da carona publica");
		System.out.println("Caronante dando a nota: " + caronaPublica.atribuirNotaCaronante(8));
		System.out.println("Caroneiro 3 dando a nota: " + caronaPublica.atribuirNotaCaroneiro(3, 10));
		
		System.out.println("\n------------------------\n");
		
		//Do jeito que foi feito até agora, o print funciona normalmente
		System.out.println("Imprimindo a carona publica");
		System.out.println(caronaPublica);
		System.out.println("\n------------------------\n");
		System.out.println("Imprimindo a carona privada");
		System.out.println(caronaPrivada);
		
		System.out.println("\n------------------------\n");
		
		System.out.println("Imprimindo o grupo privado");
		System.out.println(grupoPrivado.toStringMostraMembros(0));
		System.out.println("\n------------------------\n");
		System.out.println("Imprimindo o grupo publico");
		System.out.println(grupoPublico.toStringMostraMembros(0));
		
	}

}
