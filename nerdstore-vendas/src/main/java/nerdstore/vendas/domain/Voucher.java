package nerdstore.vendas.domain;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import nerdstore.core.communication.ValidationResult;
import nerdstore.core.domainobjects.Entity;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Getter
@Setter
public class Voucher extends Entity {
    @Id
    private String id;
    private String codigo;
    private BigDecimal percentual;
    private BigDecimal valorDesconto;
    private Integer quantidade;
    private TipoDescontoVoucher tipoDescontoVoucher;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataUtilizacao;
    private LocalDateTime dataValidade;
    private Boolean ativo;
    private Boolean utilizado;

    @Transient
    @BsonIgnore
    private List<Pedido> pedidos;

    ValidationResult validarSeAplicavel()
    {
        //return new VoucherAplicavelValidation().validate(this);
        return new ValidationResult();
    }
}

// public class VoucherAplicavelValidation : AbstractValidator<Voucher>
// {

//     public VoucherAplicavelValidation()
//     {
//         RuleFor(c => c.DataValidade)
//             .Must(DataVencimentoSuperiorAtual)
//             .WithMessage("Este voucher está expirado.");

//         RuleFor(c => c.Ativo)
//             .Equal(true)
//             .WithMessage("Este voucher não é mais válido.");

//         RuleFor(c => c.Utilizado)
//             .Equal(false)
//             .WithMessage("Este voucher já foi utilizado.");

//         RuleFor(c => c.Quantidade)
//             .GreaterThan(0)
//             .WithMessage("Este voucher não está mais disponível");
//     }

//     protected static bool DataVencimentoSuperiorAtual(DateTime dataValidade)
//     {
//         return dataValidade >= DateTime.Now;
//     }
//}