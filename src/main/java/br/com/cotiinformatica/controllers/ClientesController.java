package br.com.cotiinformatica.controllers;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.repositories.IClientesRepository;
import br.com.cotiinformatica.requests.ModalClienteRequest;
import io.swagger.annotations.ApiOperation;

@Transactional
@Controller
public class ClientesController
{
	private static final String ENDPOINT = "/api/clientes";

	@Autowired // A interface será inicializada automaticamente
	private IClientesRepository empresaRepository;

	@ApiOperation("Método para realizar o cadastro de um cliente.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody ModalClienteRequest request)
	{
		try
		{
			Cliente empresa = new Cliente();
			empresa.setNome(request.getNome());
			empresa.setCpf(request.getCpf());
			empresa.setEmail(request.getEmail());
			empresaRepository.save(empresa);

			// HttpStatus.CREATED => HTTP 201
			return ResponseEntity.status(HttpStatus.CREATED).body("Dados cadastrados com sucesso.");
		}
		catch (Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation("Método para realizar a atualização dos dados de um cliente.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody ModalClienteRequest request)
	{
		try
		{
			// pesquisar pelo ID e verificar se o registro não foi encontrado
			Optional<Cliente> consulta = empresaRepository.findById(request.getIdCliente());
			if (consulta.isEmpty())
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registro não encontrado, favor verificar o ID informado.");
			
			Cliente empresa = new Cliente();
			empresa.setNome(request.getNome());
			empresa.setCpf(request.getCpf());
			empresa.setEmail(request.getEmail());
			empresaRepository.save(empresa);

			// HttpStatus.CREATED => HTTP 201
			return ResponseEntity.status(HttpStatus.CREATED).body("Dados atualizados com sucesso.");
		}
		catch (Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation("Método para realizar a exclusão de um cliente.")
	@RequestMapping(value = ENDPOINT + "/{idCliente}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("idCliente") Integer idCliente)
	{
		try
		{
			// pesquisar pelo ID e verificar se o registro não foi encontrado
			Optional<Cliente> consulta = empresaRepository.findById(idCliente);
			if (consulta.isEmpty())
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registro não encontrado, favor verificar o ID informado.");
			
			//capturar e excluir o registro
			Cliente empresa = consulta.get();
			empresaRepository.delete(empresa);

			// HttpStatus.CREATED => HTTP 201
			return ResponseEntity.status(HttpStatus.CREATED).body("Dados excluídos com sucesso.");
		}
		catch (Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation("Método para consultar todos os registros cadastrados.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	public ResponseEntity<List<Cliente>> getAll()
	{
		try
		{
			List<Cliente> resultado = (List<Cliente>) empresaRepository.findAll();
			return ResponseEntity.status(HttpStatus.OK).body(resultado);
		}
		catch (Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation("Método para consultar 1 empresa baseado no ID (identificador da empresa).")
	@RequestMapping(value = ENDPOINT + "/{idCliente}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> getById(@PathVariable("idCliente") Integer idCliente)
	{
		try
		{
			Optional<Cliente> consulta = empresaRepository.findById(idCliente);
			// verificando se uma empresa foi encontrada
			if (consulta.isPresent())
			{
				Cliente resultado = consulta.get();
				return ResponseEntity.status(HttpStatus.OK).body(resultado);
			}			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		catch (Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}