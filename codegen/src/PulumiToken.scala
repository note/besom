package besom.codegen

import scala.util.matching.Regex

case class PulumiToken private (provider: String, module: String, name: String) {
  def asString: String  = s"${provider}:${module}:${name}"
  def uniformed: String = asString.toLowerCase
}

object PulumiToken {
  private val tokenFmtShort: String = "([^:]*):([^:]*)" // provider:name
  private val tokenPatternShort: Regex = ("^" + tokenFmtShort + "$").r
  private val tokenFmt: String      = "([^:]*):([^:]*)?:([^:]*)" // provider:module:name
  private val tokenPattern: Regex   = ("^" + tokenFmt + "$").r

  private def enforceNonEmptyModule(module: String): String =
    module match {
      case "" => Utils.indexModuleName
      case _  => module
    }

  def apply(token: String): PulumiToken = token match {
    case tokenPattern(provider, module, name) =>
      new PulumiToken(
        provider = provider,
        module = enforceNonEmptyModule(module),
        name = name
      )
    case tokenPatternShort(provider, name) =>
      new PulumiToken(
        provider = provider,
        module = Utils.indexModuleName,
        name = name
      )
    case _ => throw TypeMapperError(s"Cannot parse Pulumi token: $token, tokenPattern: $tokenPattern")
  }

  def apply(provider: String, module: String, name: String): PulumiToken = {
    new PulumiToken(
      provider = provider,
      module = enforceNonEmptyModule(module),
      name = name
    )
  }
}
